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
import java.util.*;
import java.util.stream.Collectors;

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

    /**
     * 员工评价统计
     *
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectEvaluateListByStaff() {
        // 获取所有员工信息
        List<StaffInfo> staffInfoList = staffInfoService.list(Wrappers.<StaffInfo>lambdaQuery().eq(StaffInfo::getStatus, 1));
        if (CollectionUtil.isEmpty(staffInfoList)) {
            return Collections.emptyList();
        }
        List<LinkedHashMap<String, Object>> result = new ArrayList<>();
        // 所有评价信息
        List<StaffEvaluation> evaluationList = this.list();
        if (CollectionUtil.isEmpty(evaluationList)) {
            staffInfoList.forEach(e -> {
                result.add(new LinkedHashMap<String, Object>() {
                    {
                        put("staff", e);
                        put("scheduleScore", BigDecimal.ZERO);
                        put("repairScore", BigDecimal.ZERO);
                        put("serviceScore", BigDecimal.ZERO);
                        put("score", BigDecimal.ZERO);
                    }
                });
            });
            return result;
        }
        // 将评价信息按员工code转Map
        Map<Integer, List<StaffEvaluation>> staffEvaluateMap = evaluationList.stream().collect(Collectors.groupingBy(StaffEvaluation::getStaffId));
        staffInfoList.forEach(e -> {
            List<StaffEvaluation> staffEvaluationList = staffEvaluateMap.get(e.getId());
            LinkedHashMap<String, BigDecimal> evaluateItem = new LinkedHashMap<String, BigDecimal>() {
                {
                    put("scheduleScore", BigDecimal.ZERO);
                    put("repairScore", BigDecimal.ZERO);
                    put("serviceScore", BigDecimal.ZERO);
                    put("score", BigDecimal.ZERO);
                }
            };
            if (CollectionUtil.isNotEmpty(staffEvaluationList)) {
                staffEvaluationList.forEach(evaluation -> {
                    evaluateItem.put("scheduleScore", evaluateItem.get("scheduleScore").add(evaluation.getScheduleScore()));
                    evaluateItem.put("repairScore", evaluateItem.get("repairScore").add(evaluation.getRepairScore()));
                    evaluateItem.put("serviceScore", evaluateItem.get("serviceScore").add(evaluation.getServiceScore()));
                    evaluateItem.put("score", evaluateItem.get("score").add(evaluation.getScore()));
                });

                int size = evaluationList.size();
                evaluateItem.replaceAll((k, v) -> v.divide(BigDecimal.valueOf(size), 2, RoundingMode.HALF_UP));

                LinkedHashMap<String, Object> evaluateItemAll = new LinkedHashMap<String, Object>() {
                    {
                        put("staff", e);
                    }
                };
                evaluateItem.keySet().forEach(scoreName -> {
                    evaluateItemAll.put(scoreName, evaluateItem.get(scoreName));
                });
                result.add(evaluateItemAll);
            } else {
                evaluateItem.replaceAll((k, v) -> BigDecimal.valueOf(80));
                LinkedHashMap<String, Object> evaluateItemAll = new LinkedHashMap<String, Object>() {
                    {
                        put("staff", e);
                    }
                };
                evaluateItem.keySet().forEach(scoreName -> {
                    evaluateItemAll.put(scoreName, evaluateItem.get(scoreName));
                });
                result.add(evaluateItemAll);
            }
        });
        return result;
    }
}
