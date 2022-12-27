package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.CommunityInfo;
import cc.mrbird.febs.cos.service.ICommunityInfoService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
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
@RequestMapping("/cos/community-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommunityInfoController {

    private final ICommunityInfoService communityInfoService;

    /**
     * 分页获取小区信息
     *
     * @param page          分页对象
     * @param communityInfo 小区信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<CommunityInfo> page, CommunityInfo communityInfo) {
        return R.ok(communityInfoService.selectCommunityPage(page, communityInfo));
    }

    /**
     * 根据小区编号获取小区信息
     *
     * @param code 小区编号
     * @return 结果
     */
    @GetMapping("/detail/{code}")
    public R detail(@PathVariable("code") String code) {
        return R.ok(communityInfoService.getOne(Wrappers.<CommunityInfo>lambdaQuery().eq(CommunityInfo::getCode, code)));
    }

    /**
     * 远程获取小区信息
     *
     * @param name 小区信息
     * @return 结果
     */
    @GetMapping("/remote/{name}")
    public R remoteCommunity(@PathVariable(value = "name", required = false) String name) {
        return R.ok(communityInfoService.remoteCommunity(name));
    }

    /**
     * 获取小区房屋业务统计
     *
     * @return 结果
     */
    @GetMapping("/selectHouseSell")
    public R selectHouseSell() {
        return R.ok(communityInfoService.selectHouseSell());
    }

    /**
     * 新增小区信息
     *
     * @param communityInfo 小区信息
     * @return 结果
     */
    @PostMapping
    public R save(CommunityInfo communityInfo) {
        if (StrUtil.isEmpty(communityInfo.getProvince())) {
            communityInfo.setProvince(communityInfo.getCity());
        }
        communityInfo.setCode("CMUT-" + System.currentTimeMillis());
        communityInfo.setDelFlag(0);
        communityInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(communityInfoService.save(communityInfo));
    }

    /**
     * 修改小区信息
     *
     * @param communityInfo 小区信息
     * @return 结果
     */
    @PutMapping
    public R edit(CommunityInfo communityInfo) {
        return R.ok(communityInfoService.updateById(communityInfo));
    }

    /**
     * 删除小区信息
     *
     * @param ids ids
     * @return 小区信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(communityInfoService.removeByIds(ids));
    }

    /**
     * 根据小区编号获取房屋信息
     *
     * @param code 小区编号
     * @return 结果
     */
    @GetMapping("/house/{code}")
    public R selectHouseByCode(@PathVariable("code") String code) {
        return R.ok(communityInfoService.selectHouseByCode(code));
    }

    /**
     * 根据小区编号获取租房信息
     *
     * @param code 小区编号
     * @return 结果
     */
    @GetMapping("/rent/{code}")
    public R selectHouseRentByCode(@PathVariable("code") String code) {
        return R.ok(communityInfoService.selectHouseRentByCode(code));
    }
}
