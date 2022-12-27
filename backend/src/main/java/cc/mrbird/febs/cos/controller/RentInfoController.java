package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.CommunityInfo;
import cc.mrbird.febs.cos.entity.RentInfo;
import cc.mrbird.febs.cos.entity.StaffInfo;
import cc.mrbird.febs.cos.service.ICommunityInfoService;
import cc.mrbird.febs.cos.service.IRentInfoService;
import cc.mrbird.febs.cos.service.IStaffInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
@RestController
@RequestMapping("/cos/rent-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RentInfoController {

    private final IRentInfoService rentInfoService;

    private final IStaffInfoService staffInfoService;

    private final ICommunityInfoService communityInfoService;

    /**
     * 首页标题数据
     *
     * @return 结果
     */
    @GetMapping("/home/title")
    public R selectHomeTitle() {
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("rent", rentInfoService.count(Wrappers.<RentInfo>lambdaQuery().eq(RentInfo::getFlag, 1)));
                put("community", communityInfoService.count());
                put("staff", staffInfoService.count(Wrappers.<StaffInfo>lambdaQuery().eq(StaffInfo::getStaffStatus, 1)));
            }
        };
        return R.ok(result);
    }

    /**
     * 获取首页数据
     *
     * @return 结果
     */
    @GetMapping("/home/data")
    public R selectHomeData() {
        return R.ok(rentInfoService.selectHomeData());
    }

    /**
     * 分页获取出租信息信息
     *
     * @param page     分页对象
     * @param rentInfo 出租信息信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<RentInfo> page, RentInfo rentInfo) {
        return R.ok(rentInfoService.selectRentPage(page, rentInfo));
    }

    /**
     * 设置租房信息状态
     *
     * @param rentId 出租信息ID
     * @param status 状态
     * @return 结果
     */
    @GetMapping("/setStatus")
    public R setRentStatus(@RequestParam("rentId") Integer rentId, @RequestParam("status") Integer status) {
        return R.ok(rentInfoService.update(Wrappers.<RentInfo>lambdaUpdate().set(RentInfo::getFlag, status).eq(RentInfo::getId, rentId)));
    }

    /**
     * 查询租房信息详情
     *
     * @param rentId 出租ID
     * @return 结果
     */
    @GetMapping("/detail/{rentId}")
    public R detail(@PathVariable("rentId") Integer rentId) {
        return R.ok(rentInfoService.getById(rentId));
    }

    /**
     * 获取租房状态
     *
     * @param rentId 租房ID
     * @return 结果
     */
    @GetMapping("/rent/{rentId}")
    public R getRentStatus(@PathVariable("rentId") String rentId) {
        return R.ok(rentInfoService.selectRentStatus(rentId));
    }

    /**
     * 新增出租信息信息
     *
     * @param rentInfo 出租信息信息
     * @return 结果
     */
    @PostMapping
    public R save(RentInfo rentInfo) {
        rentInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        rentInfo.setFlag(1);
        rentInfo.setViews(0);
        return R.ok(rentInfoService.save(rentInfo));
    }

    /**
     * 修改出租信息信息
     *
     * @param rentInfo 出租信息信息
     * @return 结果
     */
    @PutMapping
    public R edit(RentInfo rentInfo) {
        return R.ok(rentInfoService.updateById(rentInfo));
    }

    /**
     * 删除出租信息信息
     *
     * @param ids ids
     * @return 出租信息信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(rentInfoService.removeByIds(ids));
    }

}
