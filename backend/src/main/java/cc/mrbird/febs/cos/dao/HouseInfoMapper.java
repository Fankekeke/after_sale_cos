package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.HouseInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface HouseInfoMapper extends BaseMapper<HouseInfo> {

    /**
     * 分页获取房屋信息
     *
     * @param page 分页对象
     * @param houseInfo 房屋信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectHousePage(Page<HouseInfo> page, @Param("houseInfo") HouseInfo houseInfo);

    /**
     * 远程调用房屋信息
     *
     * @param ownerCode 业主编号
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> remoteHouse(@Param("ownerCode") String ownerCode, @Param("address") String address);
}
