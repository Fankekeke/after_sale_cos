package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.StaffEvaluation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.LinkedHashMap;

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
}
