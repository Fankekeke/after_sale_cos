package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.OrderInfo;
import cc.mrbird.febs.cos.entity.RepairInfo;
import cc.mrbird.febs.cos.service.IOrderInfoService;
import cc.mrbird.febs.cos.service.IRepairInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author FanK
 */
@RestController
@RequestMapping("/cos/repair-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RepairInfoController {

    private final IRepairInfoService repairInfoService;

    private final IOrderInfoService orderInfoService;

    /**
     * 分页获取维修信息
     *
     * @param page       分页对象
     * @param repairInfo 维修信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<RepairInfo> page, RepairInfo repairInfo) {
        return R.ok(repairInfoService.selectRepairPage(page, repairInfo));
    }

    /**
     * 根据员工获取维修信息
     *
     * @param userId userId
     * @return 结果
     */
    @GetMapping("/list/staff/{userId}")
    public R selectRepairByStaff(@PathVariable("userId") String userId) {
        return R.ok(repairInfoService.selectRepairByStaff(userId));
    }

    /**
     * 维修单详情
     *
     * @param repairCode 维修编号
     * @return 结果
     */
    @GetMapping("/detail/{repairCode}")
    public R selectRepairDetail(@PathVariable("repairCode") String repairCode) {
        return R.ok(repairInfoService.selectRepairDetail(repairCode));
    }

    /**
     * 修改维修单状态
     *
     * @param repairId 维修信息ID
     * @param status   状态
     * @return 结果
     */
    @GetMapping("/edit/status")
    @Transactional(rollbackFor = Exception.class)
    public R editRepairStatus(@RequestParam("repairId") Integer repairId, @RequestParam("status") Integer status) {
        RepairInfo repairInfo = repairInfoService.getById(repairId);
        // 获取工单信息
        OrderInfo orderInfo = orderInfoService.getOne(Wrappers.<OrderInfo>lambdaQuery().eq(OrderInfo::getRepairCode, repairInfo.getRepairCode()));
        // 更新工单状态为缴费
        if (status == 1) {
            orderInfoService.update(Wrappers.<OrderInfo>lambdaUpdate().set(OrderInfo::getStatus, 2).eq(OrderInfo::getOrderCode, orderInfo.getOrderCode()));
        }
        return R.ok(repairInfoService.update(Wrappers.<RepairInfo>lambdaUpdate().set(RepairInfo::getRepairStatus, status).eq(RepairInfo::getId, repairId)));
    }

    /**
     * 新增维修信息
     *
     * @param repairInfo 维修信息
     * @return 结果
     */
    @PostMapping
    public R save(RepairInfo repairInfo) {
        repairInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(repairInfoService.save(repairInfo));
    }

    /**
     * 修改维修信息
     *
     * @param repairInfo 维修信息
     * @return 结果
     */
    @PutMapping
    public R edit(RepairInfo repairInfo) {
        return R.ok(repairInfoService.updateById(repairInfo));
    }

    /**
     * 删除维修信息
     *
     * @param ids ids
     * @return 维修信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(repairInfoService.removeByIds(ids));
    }

}
