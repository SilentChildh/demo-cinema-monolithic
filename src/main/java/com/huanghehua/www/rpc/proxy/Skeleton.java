package com.huanghehua.www.rpc.proxy;

import com.huanghehua.www.rpc.data.ServerConfig;
import com.huanghehua.www.rpc.handler.JsonChannelOutboundHandle;
import com.huanghehua.www.rpc.handler.server.JsonServerChannelInboundHandle;
import com.huanghehua.www.rpc.handler.server.RequestServerChannelInboundHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 骨架
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/05
 */
public class Skeleton {
    private static final Logger LOGGER = Logger.getAnonymousLogger();
    /**
     * 端口与服务器配置信息的映射
     */
    private static final Map<Integer, ServerConfig> MAP = new ConcurrentHashMap<>(64);

    public static void register(String host, Integer port) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup(2);

        // 入站反序列化器
        JsonServerChannelInboundHandle jsonServerChannelInboundHandle = new JsonServerChannelInboundHandle();
        // 远程调用处理器
        RequestServerChannelInboundHandler requestServerChannelInboundHandler = new RequestServerChannelInboundHandler();
        // 出站序列化器
        JsonChannelOutboundHandle jsonChannelOutboundHandle = new JsonChannelOutboundHandle();

        try {
            ChannelFuture future = new ServerBootstrap()
                    .group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) {
                            ch.pipeline()
                                    .addLast(jsonServerChannelInboundHandle)
                                    .addLast(requestServerChannelInboundHandler)
                                    .addLast(jsonChannelOutboundHandle);
                        }
                    })
                    .bind(new InetSocketAddress(host, port)).sync();

            future.addListener(bootstrapFuture -> LOGGER.log(Level.INFO, "服务器启动 successfully"));


            // 注册端口与服务器配置信息
            ServerConfig serverConfig = new ServerConfig(boss, worker, future.channel());
            MAP.put(port, serverConfig);

        }catch (Exception e){
            LOGGER.log(Level.SEVERE, "出现异常", e);
        }
    }

    /**
     * 通过指定端口号，关闭该端口的服务
     *
     * @param port 港口
     */
    public static void close(Integer port) {
        ServerConfig serverConfig = MAP.get(port);

        NioEventLoopGroup boss = serverConfig.getBoss();
        NioEventLoopGroup worker = serverConfig.getWorker();
        Channel channel = serverConfig.getChannel();

        boss.shutdownGracefully();
        worker.shutdownGracefully();
        channel.close();

    }
}
