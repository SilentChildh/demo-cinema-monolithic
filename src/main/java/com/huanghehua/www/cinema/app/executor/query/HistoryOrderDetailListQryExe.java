package com.huanghehua.www.cinema.app.executor.query;

import com.huanghehua.www.cinema.client.dto.HistoryOrderDetailDTO;
import com.huanghehua.www.cinema.client.dto.OrderDetailDTO;
import com.huanghehua.www.cinema.client.dto.query.HistoryOrderDetailListQry;
import com.huanghehua.www.cinema.infrastructure.data.OrderPO;
import com.huanghehua.www.cinema.infrastructure.mapper.OrderMapper;
import com.huanghehua.www.common.CommonResult;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Reference;
import com.huanghehua.www.ioc.spi.aop.Interceptable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 查询订单历史列表表的 executor
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/12
 */
@Bean
@Interceptable
public class HistoryOrderDetailListQryExe extends OrderDetailGetQryExe {

    @Reference
    private OrderMapper orderMapper;

    /**
     * 通过订单id获取订单历史记录详情<br/>
     * <p/>
     * 当订单失效时，跳过添加
     *
     * @param historyOrderDetailListQry 历史订单细节qry列表
     * @return {@link CommonResult}<{@link List}<{@link HistoryOrderDetailDTO}>>
     */
    public CommonResult<List<HistoryOrderDetailDTO>> execute(HistoryOrderDetailListQry historyOrderDetailListQry) {
        ArrayList<HistoryOrderDetailDTO> historyOrderDetailList = new ArrayList<>();
        // 获取用户id
        Long userId = historyOrderDetailListQry.getUserId();


        // 获取订单id表
        List<Long> orderIdList = orderMapper.listOrderIdByUserId(userId);

        orderIdList.forEach(orderId -> {
            // 获取order持久化对象
            OrderPO orderPo = orderMapper.getOrderById(orderId);
            // 如果订单失效，则跳过添加
            if (!orderPo.getStatus()) {
                return;
            }

            // 填充历史订单表，并获取DTO
            OrderDetailDTO orderDetailDTO = super.fillOrderDetailDTO(new HistoryOrderDetailDTO(), orderPo);
            HistoryOrderDetailDTO historyOrderDetailDTO = (HistoryOrderDetailDTO) orderDetailDTO;

            // 设置历史时间
            LocalDateTime historyTime = orderMapper.getUpdateTimeById(orderId);
            historyOrderDetailDTO.setHistoryTime(historyTime);

            // 添加到list中
            historyOrderDetailList.add(historyOrderDetailDTO);
        });


        return CommonResult.operateSuccess(historyOrderDetailList);
    }

}
