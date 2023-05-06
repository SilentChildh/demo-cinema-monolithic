package com.huanghehua.www.framework.rpc.handler;

import com.alibaba.fastjson.JSON;
import com.huanghehua.rpc.data.RemoteResponseDTO;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 入站处理器，用于将服务器的返回结果，利用json反序列化为一个DTO实例
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/05
 */
@ChannelHandler.Sharable
public class JsonChannelInboundHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private static final Logger LOGGER = Logger.getAnonymousLogger();
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

        LOGGER.log(Level.INFO, "bytebuf 中的数据大小为 {0}", msg.writerIndex());
        byte[] bytes = new byte[msg.writerIndex()];
        msg.readBytes(bytes);

        RemoteResponseDTO remoteResponseDto = JSON.parseObject(bytes, RemoteResponseDTO.class);
        LOGGER.log(Level.INFO, "有消息传输 {0} ", remoteResponseDto);

        // 将处理结果传递给下一个handler
        super.channelRead(ctx, remoteResponseDto);
    }
}
