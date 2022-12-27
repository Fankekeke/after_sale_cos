package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.StaffEvaluation;
import cc.mrbird.febs.cos.service.IStaffEvaluationService;
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
@RequestMapping("/cos/staff-evaluation")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StaffEvaluationController {

    private final IStaffEvaluationService staffEvaluationService;

    /**
     * 分页获取员工评价信息
     *
     * @param page            分页对象
     * @param staffEvaluation 员工评价信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<StaffEvaluation> page, StaffEvaluation staffEvaluation) {
        return R.ok(staffEvaluationService.selectStaffEvaluationPage(page, staffEvaluation));
    }

    /**
     * 根据员工编号获取员工评价
     *
     * @param staffCode 员工编号
     * @return 结果
     */
    @GetMapping("/score/{staffCode}")
    public R selectStaffScoreByCode(@PathVariable("staffCode") String staffCode) {
        return R.ok(staffEvaluationService.selectStaffScoreByCode(staffCode));
    }

    /**
     * 新增员工评价信息
     *
     * @param staffEvaluation 员工评价信息
     * @return 结果
     */
    @PostMapping
    public R save(StaffEvaluation staffEvaluation) {
        staffEvaluation.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(staffEvaluationService.save(staffEvaluation));
    }

    /**
     * 修改员工评价信息
     *
     * @param staffEvaluation 员工评价信息
     * @return 结果
     */
    @PutMapping
    public R edit(StaffEvaluation staffEvaluation) {
        return R.ok(staffEvaluationService.updateById(staffEvaluation));
    }

    /**
     * 删除员工评价信息
     *
     * @param ids ids
     * @return 员工评价信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(staffEvaluationService.removeByIds(ids));
    }

}
