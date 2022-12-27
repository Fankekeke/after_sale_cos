package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.HousePriceTrend;
import cc.mrbird.febs.cos.service.IHousePriceTrendService;
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
@RequestMapping("/cos/house-price-trend")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HousePriceTrendController {

    private final IHousePriceTrendService housePriceTrendService;

    /**
     * 分页获取房价走势信息
     *
     * @param page            分页对象
     * @param housePriceTrend 房价走势信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<HousePriceTrend> page, HousePriceTrend housePriceTrend) {
        return R.ok(housePriceTrendService.selectPriceTrendPage(page, housePriceTrend));
    }

    /**
     * 根据小区获取走势数据
     *
     * @param year 统计年度
     * @return 结果
     */
    @GetMapping("/trend/top")
    public R selectTrendByCommunity(@RequestParam(value = "year", required = false) String year, @RequestParam(value = "province", required = false) String province) {
        return R.ok(housePriceTrendService.selectTrendByCommunity(year, province));
    }

    /**
     * 根据省份获取走势数据
     *
     * @param year 统计年度
     * @return 结果
     */
    @GetMapping("/trend/province/{year}")
    public R selectTrendByProvince(@PathVariable(value = "year", required = false) String year) {
        return R.ok(housePriceTrendService.selectTrendByProvince(year));
    }

    /**
     * 获取房价走势信息
     *
     * @param communityCode 小区编号
     * @param year          年
     * @param month         月
     * @return 结果
     */
    @GetMapping("/trend/community")
    public R selectHousePriceTrend(@RequestParam("communityCode") String communityCode, @RequestParam("year") String year, @RequestParam("month") String month) {
        return R.ok(housePriceTrendService.selectHousePriceTrend(communityCode, year, month));
    }

    /**
     * 新增房价走势信息
     *
     * @param housePriceTrend 房价走势信息
     * @return 结果
     */
    @PostMapping
    public R save(HousePriceTrend housePriceTrend) throws Exception {
        housePriceTrend.setYear(StrUtil.toString(DateUtil.year(new Date())));
        housePriceTrend.setMonth(StrUtil.toString(DateUtil.month(new Date()) + 1));
        // 判断是否重复
        int count = housePriceTrendService.count(Wrappers.<HousePriceTrend>lambdaQuery().eq(HousePriceTrend::getCommunityCode, housePriceTrend.getCommunityCode()).eq(HousePriceTrend::getYear, housePriceTrend.getYear()).eq(HousePriceTrend::getMonth, housePriceTrend.getMonth()));
        if (count >= 1) {
            throw new FebsException("此月度已添加过!");
        }
        housePriceTrend.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(housePriceTrendService.save(housePriceTrend));
    }

    /**
     * 修改房价走势信息
     *
     * @param housePriceTrend 房价走势信息
     * @return 结果
     */
    @PutMapping
    public R edit(HousePriceTrend housePriceTrend) {
        return R.ok(housePriceTrendService.updateById(housePriceTrend));
    }

    /**
     * 删除房价走势信息
     *
     * @param ids ids
     * @return 房价走势信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(housePriceTrendService.removeByIds(ids));
    }

}
