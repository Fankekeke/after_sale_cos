package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.RentEvaluation;
import cc.mrbird.febs.cos.service.IRentEvaluationService;
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
@RequestMapping("/cos/rent-evaluation")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RentEvaluationController {

    private final IRentEvaluationService rentEvaluationService;

    /**
     * 分页获取租房评价信息
     *
     * @param page           分页对象
     * @param rentEvaluation 租房评价信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<RentEvaluation> page, RentEvaluation rentEvaluation) {
        return R.ok(rentEvaluationService.selectRentEvaluationPage(page, rentEvaluation));
    }

    /**
     * 获取房屋评价信息
     *
     * @param houseCode 房屋编号
     * @return 结果
     */
    @GetMapping("/house/{houseCode}")
    public R selectEvaluationByHouse(@PathVariable("houseCode") String houseCode) {
        return R.ok(rentEvaluationService.selectEvaluationByHouse(houseCode));
    }

    /**
     * 获取小区评价信息
     *
     * @param communityCode 小区编号
     * @return 结果
     */
    @GetMapping("/community/{communityCode}")
    public R selectEvaluationByCommunity(@PathVariable("communityCode") String communityCode) {
        return R.ok(rentEvaluationService.selectEvaluationByCommunity(communityCode));
    }

    /**
     * 新增租房评价信息
     *
     * @param rentEvaluation 租房评价信息
     * @return 结果
     */
    @PostMapping
    public R save(RentEvaluation rentEvaluation) {
        rentEvaluation.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(rentEvaluationService.save(rentEvaluation));
    }

    /**
     * 修改租房评价信息
     *
     * @param rentEvaluation 租房评价信息
     * @return 结果
     */
    @PutMapping
    public R edit(RentEvaluation rentEvaluation) {
        return R.ok(rentEvaluationService.updateById(rentEvaluation));
    }

    /**
     * 删除租房评价信息
     *
     * @param ids ids
     * @return 租房评价信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(rentEvaluationService.removeByIds(ids));
    }

}
