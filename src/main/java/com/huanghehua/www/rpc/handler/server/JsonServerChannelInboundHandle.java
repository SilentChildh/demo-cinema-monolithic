package com.huanghehua.www.rpc.handler.server;

import com.alibaba.fastjson.JSON;
import com.huanghehua.rpc.data.RemoteRequestDTO;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * JSON序列化入站处理器，用于将{@code ByteBuf}缓冲区保存的json序列转换为对应的{@link RemoteRequestDTO}实例
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/05
 */
@ChannelHandler.Sharable
public class JsonServerChannelInboundHandle extends SimpleChannelInboundHandler<ByteBuf> {
    private static final Logger LOGGER = Logger.getAnonymousLogger();
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

        LOGGER.log(Level.INFO, "bytebuf 中的数据大小为 {0}", msg.writerIndex());
        byte[] bytes = new byte[msg.writerIndex()];
        msg.readBytes(bytes);


        RemoteRequestDTO remoteRequestDTO = JSON.parseObject(bytes, RemoteRequestDTO.class);
        LOGGER.log(Level.INFO, "有消息传输 {0} ", remoteRequestDTO);

        // 将处理结果传递给下一个handler
        super.channelRead(ctx, remoteRequestDTO);
    }
}
