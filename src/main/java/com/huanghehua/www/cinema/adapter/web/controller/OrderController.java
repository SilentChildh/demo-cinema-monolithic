package com.huanghehua.www.cinema.adapter.web.controller;

import com.huanghehua.www.cinema.app.service.ExhibitionServiceImpl;
import com.huanghehua.www.cinema.app.service.OrderServiceImpl;
import com.huanghehua.www.cinema.client.api.ExhibitionServiceI;
import com.huanghehua.www.cinema.client.api.OrderServiceI;
import com.huanghehua.www.cinema.client.dto.FilmDTO;
import com.huanghehua.www.cinema.client.dto.OrderDetailDTO;
import com.huanghehua.www.cinema.client.dto.HistoryOrderDetailDTO;
import com.huanghehua.www.cinema.client.dto.command.OrderAddCmd;
import com.huanghehua.www.cinema.client.dto.command.OrderRemoveCmd;
import com.huanghehua.www.cinema.client.dto.query.OrderDetailGetQry;
import com.huanghehua.www.cinema.client.dto.query.HistoryOrderDetailListQry;
import com.huanghehua.www.common.CommonResult;
import com.huanghehua.www.dispatch.annotation.Request;
import com.huanghehua.www.dispatch.annotation.RequestParam;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Reference;

import java.util.List;


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
     * 取消订单
     *
     * @return {@link CommonResult}<{@link ?}>
     */
    @Request(value = "/cancel", method = "post")
    public CommonResult<?> cancelOrder(OrderRemoveCmd orderRemoveCmd) {
        return orderService.cancel(orderRemoveCmd);
    }

    /**
     * 根据订单id，显示订单信息
     *
     * @param orderId 订单id
     * @return {@link CommonResult}<{@link OrderDetailDTO}>
     */
    @Request(value = "/info", method = "get")
    public CommonResult<OrderDetailDTO> showOrderDetail(@RequestParam("orderId") Long orderId) {
        // 转换为qry模型
        OrderDetailGetQry orderDetailGetQry = new OrderDetailGetQry(orderId);
        // 获取DTO模型
        CommonResult<OrderDetailDTO> orderDetaiCommonResult = orderService.showOrderDetail(orderDetailGetQry);
        OrderDetailDTO orderDetailDTO = orderDetaiCommonResult.getData();

        // 填充电影相关信息
        this.fillOrderDetailWithFilm(orderDetailDTO);

        return orderDetaiCommonResult;
    }


    /**
     * 根据用户id，查询用户订单历史记录表
     *
     * @param userId 用户id
     * @return {@link CommonResult}<{@link List}<{@link HistoryOrderDetailDTO}>>
     */
    @Request(value = "/order-history", method = "get")
    public CommonResult<List<HistoryOrderDetailDTO>> showHistoryOrderDetail(@RequestParam("userId") Long userId) {
        // 转换为qry模型
        HistoryOrderDetailListQry historyOrderDetailListQry = new HistoryOrderDetailListQry(userId);

        // 获取历史记录表
        CommonResult<List<HistoryOrderDetailDTO>> listOrderHistory =
                orderService.showListOrderHistory(historyOrderDetailListQry);
        List<HistoryOrderDetailDTO> data = listOrderHistory.getData();

        // 对每一个历史订单记录都填充电影相关信息
        data.forEach(this::fillOrderDetailWithFilm);

        return listOrderHistory;
    }

    /**
     * 查询传入的订单信息中的filmId，然后将该电影的相关信息填充入DTO中
     * 最后返回指向同一引用的DTO
     *
     * @param orderDetailDTO 订单细节dto
     * @return {@link OrderDetailDTO}
     */
    private OrderDetailDTO fillOrderDetailWithFilm(OrderDetailDTO orderDetailDTO) {
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

        return orderDetailDTO;
    }

}
