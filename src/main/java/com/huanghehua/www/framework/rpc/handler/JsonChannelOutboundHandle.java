package com.huanghehua.www.framework.rpc.handler;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 出站处理器，用于将一个正常的Object实例利用json序列化，并保存在ByteBuf中
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/05
 */
@ChannelHandler.Sharable
public class JsonChannelOutboundHandle extends ChannelOutboundHandlerAdapter {
    private static final Logger LOGGER = Logger.getAnonymousLogger();

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        LOGGER.log(Level.INFO, "写入信息为 {0}", msg);
        // 将结果json序列化至byteBuf中
        byte[] bytes = JSON.toJSONBytes(msg);
        ByteBuf byteBuf = ctx.alloc().buffer();
        byteBuf.writeBytes(bytes);
        LOGGER.log(Level.INFO, "当前写入的字节数 ： {0}", bytes.length);

        // 写入通道
        ctx.writeAndFlush(byteBuf);
        super.write(ctx, msg, promise);
    }
}
