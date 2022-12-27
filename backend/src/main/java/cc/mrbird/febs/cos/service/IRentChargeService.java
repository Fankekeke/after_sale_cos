package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.controller.po.RentChargePo;
import cc.mrbird.febs.cos.entity.RentCharge;
import cc.mrbird.febs.cos.entity.vo.RentChargeDetailVo;
import cc.mrbird.febs.cos.entity.vo.RentChargeVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface IRentChargeService extends IService<RentCharge> {

    /**
     * 查询租房信息包括房屋编号与小区编号
     *
     * @return 结果
     */
    List<RentCharge> selectRentChargeWithHouse();

    /**
     * 获取房屋出租负责信息
     *
     * @param rentCharge 参数
     * @return 结果
     */
    List<RentChargeVo> selectRentChargeList(RentChargePo rentCharge);

    /**
     * 获取租房信息详情
     *
     * @param chargeId id
     * @return 结果
     */
    RentChargeDetailVo selectChargeDetail(Integer chargeId);

    /**
     * 获取房屋租赁信息地图
     *
     * @param rentCharge 参数
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectRentChargeByMap(RentChargePo rentCharge);

    /**
     * 根据时间获取去出租信息
     *
     * @param year  统计年度
     * @param month 统计月度
     * @return 结果
     */
    List<RentChargeVo> selectRentChargeByDate(String year, String month);

    /**
     * 获取各小区租房情况
     *
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectRentChargeByCommunity(String communityCode);

    /**
     * 房屋出租信息
     *
     * @param houseCode 房屋编号
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectRentChargeByHouse(String houseCode);

    /**
     * 获取员工负责情况
     *
     * @param staffCode 员工编号
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectRentChargeByStaff(String staffCode);

    /**
     * 根据省份查询租房信息
     *
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectHouseInfoByProvince();

    /**
     * 获取员工销售情况
     *
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectRentTopByStaff();

    /**
     * 根据小区编号获取当前房源
     *
     * @param communityCode 小区编号
     * @return 结果
     */
    Integer selectRentCountByCommunity(String communityCode);
}
