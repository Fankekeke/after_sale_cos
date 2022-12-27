package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.HouseInfo;
import cc.mrbird.febs.cos.service.IHouseInfoService;
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
@RequestMapping("/cos/house-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HouseInfoController {

    private final IHouseInfoService houseInfoService;

    /**
     * 分页获取房屋信息
     *
     * @param page      分页对象
     * @param houseInfo 房屋信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<HouseInfo> page, HouseInfo houseInfo) {
        return R.ok(houseInfoService.selectHousePage(page, houseInfo));
    }

    /**
     * 查询房屋详细信息
     *
     * @param houseCode 房屋编号
     * @return 结果
     */
    @GetMapping("/detail/{houseCode}")
    public R selectDetail(@PathVariable("houseCode") String houseCode) {
        return R.ok(houseInfoService.getOne(Wrappers.<HouseInfo>lambdaQuery().eq(HouseInfo::getCode, houseCode)));
    }

    /**
     * 远程调用房屋信息
     *
     * @param ownerCode 业主编号
     * @return 结果
     */
    @GetMapping("/remote")
    public R remoteHouse(@RequestParam(value = "ownerCode", required = false) String ownerCode, @RequestParam(value = "address", required = false) String address) {
        return R.ok(houseInfoService.remoteHouse(ownerCode, address));
    }

    /**
     * 统计各省份数据
     *
     * @return 结果
     */
    @GetMapping("/province")
    public R selectHouseInfoByProvince() {
        return R.ok();
    }

    /**
     * 新增房屋信息
     *
     * @param houseInfo 房屋信息
     * @return 结果
     */
    @PostMapping
    public R save(HouseInfo houseInfo) {
        if (StrUtil.isEmpty(houseInfo.getProvince())) {
            houseInfo.setProvince(houseInfo.getCity());
        }
        houseInfo.setCode("HOUSE-" + System.currentTimeMillis());
        houseInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        houseInfo.setDelFlag(0);
        return R.ok(houseInfoService.save(houseInfo));
    }

    /**
     * 修改房屋信息
     *
     * @param houseInfo 房屋信息
     * @return 结果
     */
    @PutMapping
    public R edit(HouseInfo houseInfo) {
        return R.ok(houseInfoService.updateById(houseInfo));
    }

    /**
     * 删除房屋信息
     *
     * @param ids ids
     * @return 房屋信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(houseInfoService.removeByIds(ids));
    }

    /**
     * 获取房屋详细信息
     *
     * @param code 房屋编号
     * @return 结果
     */
    @GetMapping("/{code}")
    public R detail(@PathVariable("code") String code) {
        return R.ok(houseInfoService.getOne(Wrappers.<HouseInfo>lambdaQuery().eq(HouseInfo::getCode, code)));
    }
}
