package com.huanghehua.www.cinema.app.executor.query;

import com.huanghehua.www.cinema.client.dto.HistoryOrderDetailDTO;
import com.huanghehua.www.cinema.client.dto.OrderDetailDTO;
import com.huanghehua.www.cinema.client.dto.query.HistoryOrderDetailListQry;
import com.huanghehua.www.cinema.infrastructure.mapper.HistoryOrderMapper;
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
    private HistoryOrderMapper historyOrderMapper;

    public CommonResult<List<HistoryOrderDetailDTO>> execute(HistoryOrderDetailListQry historyOrderDetailListQry) {
        ArrayList<HistoryOrderDetailDTO> historyOrderDetailList = new ArrayList<>();
        // 获取用户id
        Long userId = historyOrderDetailListQry.getUserId();


        // 获取订单id表
        List<Long> orderIdList = historyOrderMapper.listOrderIdByUserId(userId);

        orderIdList.forEach(orderId -> {
            // 填充历史订单表
            OrderDetailDTO orderDetailDTO = super.fillOrderDetailDTO(new HistoryOrderDetailDTO(), orderId);
            HistoryOrderDetailDTO historyOrderDetailDTO = (HistoryOrderDetailDTO) orderDetailDTO;

            // 设置历史时间

            LocalDateTime historyTime = historyOrderMapper.getUpdateTimeByOrderId(orderId);
            historyOrderDetailDTO.setHistoryTime(historyTime);

            historyOrderDetailList.add(historyOrderDetailDTO);
        });


        return CommonResult.operateSuccess(historyOrderDetailList);
    }

}
