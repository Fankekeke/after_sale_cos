package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.StaffEvaluation;
import cc.mrbird.febs.cos.dao.StaffEvaluationMapper;
import cc.mrbird.febs.cos.service.IStaffEvaluationService;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.List;

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
    public IPage<LinkedHashMap<String, Object>> selectStaffEvaluationPage(Page<StaffEvaluation> page, StaffEvaluation staffEvaluation) {
        return baseMapper.selectStaffEvaluationPage(page, staffEvaluation);
    }

    /**
     * 根据员工编号获取员工评价
     *
     * @param staffCode 员工编号
     * @return 结果
     */
    @Override
    public BigDecimal selectStaffScoreByCode(String staffCode) {
        // 查询员工评价
        List<StaffEvaluation> staffEvaluationList = this.list(Wrappers.<StaffEvaluation>lambdaQuery().eq(StaffEvaluation::getStaffCode, staffCode));
        if (CollectionUtil.isEmpty(staffEvaluationList)) {
            return BigDecimal.valueOf(80);
        }
        BigDecimal scoreAll = staffEvaluationList.stream().filter(e -> e.getServiceScore() != null).map(StaffEvaluation::getServiceScore).reduce(BigDecimal.ZERO, BigDecimal::add);
        return scoreAll.divide(BigDecimal.valueOf(staffEvaluationList.size()), 2, RoundingMode.HALF_UP);
    }
}
