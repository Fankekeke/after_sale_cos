package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.StaffInfo;
import cc.mrbird.febs.cos.service.IStaffInfoService;
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
@RequestMapping("/cos/staff-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StaffInfoController {

    private final IStaffInfoService staffInfoService;

    /**
     * 获取员工分页信息
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
     * 获取员工信息
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(staffInfoService.list());
    }

    @GetMapping("/staff/{userId}")
    public R selectStaffByUserId(@PathVariable("userId") Integer userId) {
        return R.ok(staffInfoService.getOne(Wrappers.<StaffInfo>lambdaQuery().eq(StaffInfo::getSysUserId, userId)));
    }

    /**
     * 获取员工详细信息
     *
     * @param staffCode 员工编号
     * @return 结果
     */
    @GetMapping("/detail/{staffCode}")
    public R detail(@PathVariable("staffCode") String staffCode) {
        return R.ok(staffInfoService.getOne(Wrappers.<StaffInfo>lambdaQuery().eq(StaffInfo::getStaffCode, staffCode)));
    }

    /**
     * 获取员工业务统计
     *
     * @param staffType 员工类型
     * @return 结果
     */
    @GetMapping("/kpi")
    public R getStaffKpi(@RequestParam("staffType") Integer staffType) throws Exception {
        return R.ok(staffInfoService.selectStaffKpi(staffType));
    }

    /**
     * 获取员工信息
     *
     * @param type 员工类型
     * @return 结果
     */
    @GetMapping("/list/{type}")
    public R list(@PathVariable("type") Integer type) {
        return R.ok(staffInfoService.list(Wrappers.<StaffInfo>lambdaQuery().eq(StaffInfo::getStaffStatus, 1).eq(StaffInfo::getStaffType, type)));
    }

    /**
     * 添加员工信息
     *
     * @param staffInfo 员工信息
     * @return 结果
     */
    @PostMapping
    public R save(StaffInfo staffInfo) {
        staffInfo.setStaffCode("STAFF-" + System.currentTimeMillis());
        staffInfo.setStaffStatus(1);
        staffInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(staffInfoService.save(staffInfo));
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
