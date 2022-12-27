package cc.mrbird.febs.salm.service.impl;

import cc.mrbird.febs.salm.entity.StaffEvaluation;
import cc.mrbird.febs.salm.dao.StaffEvaluationMapper;
import cc.mrbird.febs.salm.service.IStaffEvaluationService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class StaffEvaluationServiceImpl extends ServiceImpl<StaffEvaluationMapper, StaffEvaluation> implements IStaffEvaluationService {

    /**
     * 分页获取员工评价信息
     *
     * @param page 分页对象
     * @param staffEvaluation 员工评价信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectEvaluatePage(Page<StaffEvaluation> page, StaffEvaluation staffEvaluation) {
        return baseMapper.selectEvaluatePage(page, staffEvaluation);
    }
}
