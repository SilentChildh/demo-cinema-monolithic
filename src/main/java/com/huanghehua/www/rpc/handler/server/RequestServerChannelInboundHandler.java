package com.huanghehua.www.rpc.handler.server;

import com.huanghehua.rpc.data.RemoteRequestDTO;
import com.huanghehua.rpc.data.RemoteResponseDTO;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 请求入站处理器， 得到请求需要调用的远程方法相关信息，并在该处理器中进行调用，然后将调用返回的结果写入通道中
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/03
 */
@ChannelHandler.Sharable
public class RequestServerChannelInboundHandler extends SimpleChannelInboundHandler<RemoteRequestDTO> {
    private static final Logger LOGGER = Logger.getLogger("com.huanghehua.www.handler.RequestHandler");
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RemoteRequestDTO request) throws Exception {
        LOGGER.log(Level.INFO, "进入RequestHandler, msg为： {0}", request);
        RemoteResponseDTO response = new RemoteResponseDTO();

        Class<?> interfaceClass;
        Class<?> instanceClass = request.getInstanceClass();
        Method method;
        Object returnVal;

        try {
            interfaceClass = request.getInterfaceClass();
            method = interfaceClass.getMethod(request.getMethodName(), request.getParameterTypes());
            returnVal = method.invoke(instanceClass.newInstance() , request.getParameterValues());

            // 封装响应结果
            response.setRequestId(request.getRequestId());
            response.setReturnValue(returnVal);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "出现异常", e);
            //出现异常封装好异常信息后传出
            response.setExceptionValue(e);
        }

        LOGGER.log(Level.INFO, "返回结果 {0} , channel : {1}", new Object[]{response, ctx.channel()});
        // 写入响应结果到管道之中
        ctx.channel().writeAndFlush(response);
    }
}
