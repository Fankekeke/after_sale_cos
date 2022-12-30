package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.StaffEvaluation;
import cc.mrbird.febs.cos.dao.StaffEvaluationMapper;
import cc.mrbird.febs.cos.entity.StaffInfo;
import cc.mrbird.febs.cos.service.IStaffEvaluationService;
import cc.mrbird.febs.cos.service.IStaffInfoService;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StaffEvaluationServiceImpl extends ServiceImpl<StaffEvaluationMapper, StaffEvaluation> implements IStaffEvaluationService {

    private final IStaffInfoService staffInfoService;

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

    /**
     * 根据员工查询综合评价信息
     *
     * @param staffId 员工ID
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, BigDecimal> selectEvaluateByStaff(Integer staffId) {
        if (staffId == null) {
            return null;
        }
        // 员工信息
        StaffInfo staffInfo = staffInfoService.getById(staffId);
        if (staffInfo == null) {
            return null;
        }
        LinkedHashMap<String, BigDecimal> result = new LinkedHashMap<String, BigDecimal>() {
            {
                put("scheduleScore", BigDecimal.ZERO);
                put("repairScore", BigDecimal.ZERO);
                put("serviceScore", BigDecimal.ZERO);
                put("score", BigDecimal.ZERO);
            }
        };
        List<StaffEvaluation> evaluationList = this.list(Wrappers.<StaffEvaluation>lambdaQuery().eq(StaffEvaluation::getStaffId, staffId));
        if (CollectionUtil.isEmpty(evaluationList)) {
            result.replaceAll((k, v) -> BigDecimal.valueOf(80));
            return result;
        }

        // 使用并行流遍历 rentEvaluationList 集合
        evaluationList.parallelStream().forEach(evaluation -> {
            result.put("scheduleScore", result.get("scheduleScore").add(evaluation.getScheduleScore()));
            result.put("repairScore", result.get("repairScore").add(evaluation.getRepairScore()));
            result.put("serviceScore", result.get("serviceScore").add(evaluation.getServiceScore()));
            result.put("score", result.get("score").add(evaluation.getScore()));
        });

        int size = evaluationList.size();
        result.replaceAll((k, v) -> v.divide(BigDecimal.valueOf(size), 2, RoundingMode.HALF_UP));

        return result;
    }
}
