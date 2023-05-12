package com.huanghehua.www.cinema.adapter.web.controller;

import com.huanghehua.www.cinema.app.service.ExhibitionServiceImpl;
import com.huanghehua.www.cinema.app.service.OrderServiceImpl;
import com.huanghehua.www.cinema.client.api.ExhibitionServiceI;
import com.huanghehua.www.cinema.client.api.OrderServiceI;
import com.huanghehua.www.cinema.client.dto.FilmDTO;
import com.huanghehua.www.cinema.client.dto.OrderDetailDTO;
import com.huanghehua.www.cinema.client.dto.command.OrderAddCmd;
import com.huanghehua.www.cinema.client.dto.query.OrderGetQry;
import com.huanghehua.www.common.CommonResult;
import com.huanghehua.www.dispatch.annotation.Request;
import com.huanghehua.www.dispatch.annotation.RequestParam;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Reference;


/**
 * 订单控制器
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/07
 */
@Bean
@Request("/reservation")
public class OrderController {
    @Reference(OrderServiceImpl.class)
    private OrderServiceI orderService;
    @Reference(ExhibitionServiceImpl.class)
    private ExhibitionServiceI exhibitionService;

    /**
     * 下订单
     *
     * @param orderAddCmd 订单添加cmd
     * @return {@link CommonResult}<{@link ?}>
     */
    @Request(value = "/order", method = "post")
    public CommonResult<?> order(OrderAddCmd orderAddCmd) {
         return orderService.order(orderAddCmd);
    }

    /**
     * 根据订单id，显示订单信息
     *
     * @param orderId 订单id
     * @return {@link CommonResult}<{@link OrderDetailDTO}>
     */
    @Request(value = "/info", method = "get")
    public CommonResult<OrderDetailDTO> showOrderInfo(@RequestParam("orderId") Long orderId) {
        // 转换为qry模型
        OrderGetQry orderGetQry = new OrderGetQry(orderId);
        // 获取DTO模型
        CommonResult<OrderDetailDTO> orderDetaiCommonResult = orderService.showOrderInfo(orderGetQry);
        OrderDetailDTO orderDetailDTO = orderDetaiCommonResult.getData();

        // 先获取电影id
        Long filmId = orderDetailDTO.getFilmId();
        // 再通过二方包获取影片信息，然后组装到orderDetailDTO中
        CommonResult<FilmDTO> filmCommonResult = exhibitionService.getFilmInfo(filmId);
        FilmDTO filmDTO = filmCommonResult.getData();

        orderDetailDTO.setFilmName(filmDTO.getName());
        orderDetailDTO.setDirector(filmDTO.getDirector());
        orderDetailDTO.setActor(filmDTO.getActor());
        orderDetailDTO.setReleaseTime(filmDTO.getReleaseTime());
        orderDetailDTO.setDuration(filmDTO.getDuration());
        orderDetailDTO.setPoster(filmDTO.getPoster());

        return orderDetaiCommonResult;
    }
}
