package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.StaffEvaluation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface IStaffEvaluationService extends IService<StaffEvaluation> {

    /**
     * 分页获取员工评价信息
     *
     * @param page 分页对象
     * @param staffEvaluation 员工评价信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectEvaluatePage(Page<StaffEvaluation> page, StaffEvaluation staffEvaluation);

    /**
     * 根据员工查询综合评价信息
     *
     * @param staffId 员工ID
     * @return 结果
     */
    LinkedHashMap<String, BigDecimal> selectEvaluateByStaff(Integer staffId);

    /**
     * 员工评价统计
     *
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectEvaluateListByStaff();
}
