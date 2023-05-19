package com.huanghehua.www.rpc.handler;

import com.huanghehua.www.rpc.data.RemoteResponseDTO;
import com.huanghehua.www.rpc.proxy.Stub;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.Promise;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 处理程序
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/03
 */
@ChannelHandler.Sharable
public class ResponseChannelInboundHandler extends SimpleChannelInboundHandler<RemoteResponseDTO> {
    private static final Logger LOGGER = Logger.getLogger("com.huanghehua.www.api.Handler");

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RemoteResponseDTO msg) throws Exception {
        LOGGER.log(Level.INFO, "反序列化后 DTO 数据为{0}", msg);

        Map<Integer, Promise<Object>> promiseMap = Stub.getPromiseMap();
        Promise<Object> objectPromise = promiseMap.get(msg.getRequestId());
        objectPromise.setSuccess(msg.getReturnValue());

    }
}
