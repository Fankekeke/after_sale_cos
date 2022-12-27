package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.controller.po.RentChargePo;
import cc.mrbird.febs.cos.entity.RentCharge;
import cc.mrbird.febs.cos.entity.vo.RentChargeDetailVo;
import cc.mrbird.febs.cos.entity.vo.RentChargeEvaluationVo;
import cc.mrbird.febs.cos.entity.vo.RentChargeVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface RentChargeMapper extends BaseMapper<RentCharge> {

    /**
     * 查询租房信息包括房屋编号与小区编号
     *
     * @return 结果
     */
    List<RentCharge> selectRentChargeWithHouse();

    /**
     * 查询租房信息以及房屋小区信息
     *
     * @param rentCharge 参数
     * @return 结果
     */
    List<RentChargeVo> selectRentChargeList(@Param("rentCharge") RentChargePo rentCharge);

    /**
     * 获取租房信息详情
     *
     * @param chargeId id
     * @return 结果
     */
    RentChargeDetailVo selectChargeDetail(@Param("chargeId") Integer chargeId);

    /**
     * 查询未评价数据
     *
     * @return 结果
     */
    List<RentChargeEvaluationVo> selectLastRentEvaluation();
}
