package cc.mrbird.febs.salm.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.salm.entity.OrderInfo;
import cc.mrbird.febs.salm.service.IOrderInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author FanK
 */
@RestController
@RequestMapping("/manage/order-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderInfoController {

    private final IOrderInfoService orderInfoService;

    /**
     * 分页获取工单信息
     *
     * @param page 分页对象
     * @param orderInfo 工单信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<OrderInfo> page, OrderInfo orderInfo) {
        return R.ok();
    }

    /**
     * 新增工单信息
     *
     * @param orderInfo 工单信息
     * @return 结果
     */
    @PostMapping
    public R save(OrderInfo orderInfo) {
        orderInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(orderInfoService.save(orderInfo));
    }

    /**
     * 修改工单信息
     *
     * @param orderInfo 工单信息
     * @return 结果
     */
    @PutMapping
    public R edit(OrderInfo orderInfo) {
        return R.ok(orderInfoService.updateById(orderInfo));
    }

    /**
     * 删除工单信息
     *
     * @param ids ids
     * @return 工单信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(orderInfoService.removeByIds(ids));
    }
}
