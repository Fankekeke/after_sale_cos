package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.RentEvaluation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IRentEvaluationService extends IService<RentEvaluation> {

    /**
     * 分页获取租房评价信息
     *
     * @param page 分页对象
     * @param rentEvaluation 租房评价信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectRentEvaluationPage(Page<RentEvaluation> page, RentEvaluation rentEvaluation);

    /**
     * 获取房屋评价信息
     *
     * @param houseCode 房屋编号
     * @return 结果
     */
    LinkedHashMap<String, BigDecimal> selectEvaluationByHouse(String houseCode);

    /**
     * 获取小区评价信息
     *
     * @param communityCode 小区编号
     * @return 结果
     */
    LinkedHashMap<String, BigDecimal> selectEvaluationByCommunity(String communityCode);
}
