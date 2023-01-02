package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.ReserveInfo;
import cc.mrbird.febs.cos.service.IReserveInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
@RequestMapping("/cos/reserve-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReserveInfoController {

    private final IReserveInfoService reserveInfoService;

    /**
     * 分页获取预约信息
     *
     * @param page        分页对象
     * @param reserveInfo 预约信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<ReserveInfo> page, ReserveInfo reserveInfo) {
        return R.ok(reserveInfoService.selectReservePage(page, reserveInfo));
    }

    /**
     * 查询预约详情
     *
     * @param orderCode 工单编号
     * @return 结果
     */
    @GetMapping("/detail/{orderCode}")
    public R selectReserveDetail(@PathVariable("orderCode") String orderCode) {
        return R.ok(reserveInfoService.getOne(Wrappers.<ReserveInfo>lambdaQuery().eq(ReserveInfo::getOrderCode, orderCode)));
    }

    /**
     * 修改预约信息状态
     *
     * @param reserveId 预约ID
     * @param status    状态
     * @return 结果
     */
    @GetMapping("/edit/status")
    public R editReserveStatus(@RequestParam("reserveId") Integer reserveId, @RequestParam("status") Integer status) {
        return R.ok(reserveInfoService.update(Wrappers.<ReserveInfo>lambdaUpdate().set(ReserveInfo::getOpenFlag, status).eq(ReserveInfo::getId, reserveId)));
    }

    /**
     * 新增预约信息
     *
     * @param reserveInfo 预约信息
     * @return 结果
     */
    @PostMapping
    public R save(ReserveInfo reserveInfo) {
        reserveInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(reserveInfoService.save(reserveInfo));
    }

    /**
     * 修改预约信息
     *
     * @param reserveInfo 预约信息
     * @return 结果
     */
    @PutMapping
    public R edit(ReserveInfo reserveInfo) {
        return R.ok(reserveInfoService.updateById(reserveInfo));
    }

    /**
     * 删除预约信息
     *
     * @param ids ids
     * @return 预约信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(reserveInfoService.removeByIds(ids));
    }

}
