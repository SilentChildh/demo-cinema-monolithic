package com.huanghehua.www.rpc.proxy;

import com.huanghehua.www.rpc.data.RemoteRequestDTO;
import com.huanghehua.www.rpc.handler.JsonChannelInboundHandler;
import com.huanghehua.www.rpc.handler.JsonChannelOutboundHandle;
import com.huanghehua.www.rpc.handler.ResponseChannelInboundHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Promise;

import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 存根
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/03
 */
public class Stub {
    private static final Logger LOGGER = Logger.getLogger("com.huanghehua.www.proxy.Stub");
    /**
     * 线程安全的通道
     */
    private static final AtomicReference<Channel> CHANNEL = new AtomicReference<>();
    /**
     * 远程调用任务映射
     */
    private static final Map<Integer, Promise<Object>> PROMISE_MAP = new ConcurrentHashMap(64);
    /**
     * 并发自增器，用于生成远程请求id
     */
    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();

    /**
     * 得到本地方法存根
     *
     * @param interfaceClass 接口类
     * @param instanceClass  实例类
     * @return {@link T}
     */
    @SuppressWarnings("unchecked")
    public static <T> T getStub(Class<T> interfaceClass, Class<?> instanceClass) {

        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), instanceClass.getInterfaces(),
                (proxy, method, args) -> {
                    // 如果调用的不是接口的方法，则抛出异常
                    if (method.getDeclaringClass() != interfaceClass) {
                        LOGGER.log(Level.SEVERE, "No Such Method");
                        throw new RuntimeException("No Such Method");
                    }
                    // 封装远程方法信息
                    RemoteRequestDTO message = new RemoteRequestDTO(ATOMIC_INTEGER.getAndIncrement(), interfaceClass,
                            instanceClass, method.getName(), method.getParameterTypes(), args);

                    // 设置通道
                    CHANNEL.set(getChannel());

                    // 提交请求，创建请求任务
                    PROMISE_MAP.put(message.getRequestId(), new DefaultPromise<>(CHANNEL.get().eventLoop()));

                    // 远程调用
                    CHANNEL.get().writeAndFlush(message);
                    // 获取远程调用结果
                    return getPromiseMap().get(message.getRequestId()).get();
                });
    }

    private static Channel getChannel() throws ExecutionException, InterruptedException {
        if (CHANNEL.get() != null) {
            return CHANNEL.get();
        }

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Channel> future = executorService.submit(() -> {
            // 接收新开启的通道
            Channel newChannel = null;

            JsonChannelOutboundHandle jsonChannelOutboundHandle = new JsonChannelOutboundHandle();
            JsonChannelInboundHandler jsonChannelInboundHandler = new JsonChannelInboundHandler();
            ResponseChannelInboundHandler responseChannelInboundHandler = new ResponseChannelInboundHandler();

            // 工作者线程组
            NioEventLoopGroup worker = new NioEventLoopGroup();
            try {
                // 得到异步任务
                ChannelFuture channelFuture = new Bootstrap()
                        .group(worker)
                        .channel(NioSocketChannel.class)
                        .handler(new ChannelInitializer<NioSocketChannel>() {
                            @Override
                            protected void initChannel(NioSocketChannel ch) {

                                ch.pipeline()
                                        .addLast(jsonChannelOutboundHandle)
                                        .addLast(jsonChannelInboundHandler)
                                        .addLast(responseChannelInboundHandler);
                            }
                        })
                        .connect("127.0.0.1", 8090).sync();

                // 通过异步任务得到开启的新通道
                newChannel = channelFuture.channel();
                LOGGER.log(Level.INFO, "客户端连接成功！");

                // 通过新通道获取异步关闭任务，并添加一个在关闭事件触发后的hook函数
                newChannel.closeFuture().addListener(closeFuture -> {

                    LOGGER.log(Level.INFO, "客户端关闭连接！{0}", closeFuture);
                    worker.shutdownGracefully();
                });

                return newChannel;
            } catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, "中断异常！");
            } finally {
                CHANNEL.set(newChannel);
            }
            return null;
        });

        return future.get();
    }


    /**
     * 远程调用任务映射
     */
    public static Map<Integer, Promise<Object>> getPromiseMap() {
        return PROMISE_MAP;
    }
}
