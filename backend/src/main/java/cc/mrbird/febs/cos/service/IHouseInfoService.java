package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.HouseInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface IHouseInfoService extends IService<HouseInfo> {

    /**
     * 分页获取房屋信息
     *
     * @param page 分页对象
     * @param houseInfo 房屋信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectHousePage(Page<HouseInfo> page, HouseInfo houseInfo);

    /**
     * 远程调用房屋信息
     *
     * @param ownerCode 业主编号
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> remoteHouse(String ownerCode, String address);

    /**
     * 统计各省份数据
     *
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectHouseInfoByProvince();
}
