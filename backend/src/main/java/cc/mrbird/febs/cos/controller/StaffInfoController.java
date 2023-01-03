package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.StaffInfo;
import cc.mrbird.febs.cos.service.IStaffInfoService;
import cc.mrbird.febs.system.domain.User;
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
@RequestMapping("/cos/staff-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StaffInfoController {

    private final IStaffInfoService staffInfoService;

    /**
     * 分页获取员工信息
     *
     * @param page      分页对象
     * @param staffInfo 员工信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<StaffInfo> page, StaffInfo staffInfo) {
        return R.ok(staffInfoService.selectStaffPage(page, staffInfo));
    }

    /**
     * 查询员工信息详情
     *
     * @param staffId 员工ID
     * @return 结果
     */
    @GetMapping("/detail/{staffId}")
    public R selectStaffById(@PathVariable("staffId") Integer staffId) {
        return R.ok(staffInfoService.getById(staffId));
    }

    /**
     * 查询员工工作状态
     *
     * @return 结果
     */
    @GetMapping("/workStatus")
    public R selectStaffWorkStatus() {
        return R.ok(staffInfoService.selectStaffWorkStatus());
    }

    /**
     * 查询所有员工信息
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(staffInfoService.list(Wrappers.<StaffInfo>lambdaQuery().eq(StaffInfo::getStatus, 1)));
    }

    /**
     * 新增员工信息
     *
     * @param staffInfo 员工信息
     * @return 结果
     */
    @PostMapping
    public R save(StaffInfo staffInfo) throws Exception {
        staffInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(staffInfoService.saveStaff(staffInfo));
    }

    /**
     * 获取员工工作情况
     *
     * @param productId 产品ID
     * @return 结果
     */
    @GetMapping("/work/{productId}")
    public R selectStaffWork(@PathVariable(value = "productId", required = false) Integer productId) {
        return R.ok(staffInfoService.selectStaffWork(productId));
    }

    /**
     * 更新员工状态
     *
     * @param staffId 员工ID
     * @param status  状态
     * @return 结果
     */
    @PostMapping("/account/status")
    public R accountStatusEdit(@RequestParam("staffId") Integer staffId, @RequestParam("status") Integer status) throws Exception {
        return R.ok(staffInfoService.accountStatusEdit(staffId, status));
    }

    /**
     * 修改员工信息
     *
     * @param staffInfo 员工信息
     * @return 结果
     */
    @PutMapping
    public R edit(StaffInfo staffInfo) {
        return R.ok(staffInfoService.updateById(staffInfo));
    }

    /**
     * 删除员工信息
     *
     * @param ids ids
     * @return 员工信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(staffInfoService.removeByIds(ids));
    }

}
