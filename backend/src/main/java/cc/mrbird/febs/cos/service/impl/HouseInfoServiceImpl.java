package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.HouseInfo;
import cc.mrbird.febs.cos.dao.HouseInfoMapper;
import cc.mrbird.febs.cos.service.IHouseInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
@Service
public class HouseInfoServiceImpl extends ServiceImpl<HouseInfoMapper, HouseInfo> implements IHouseInfoService {

    /**
     * 分页获取房屋信息
     *
     * @param page 分页对象
     * @param houseInfo 房屋信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectHousePage(Page<HouseInfo> page, HouseInfo houseInfo) {
        return baseMapper.selectHousePage(page, houseInfo);
    }

    /**
     * 远程调用房屋信息
     *
     * @param ownerCode 业主编号
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> remoteHouse(String ownerCode, String address) {
        return baseMapper.remoteHouse(ownerCode, address);
    }

    /**
     * 统计各省份数据
     *
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectHouseInfoByProvince() {
        // 获取所有
        return null;
    }
}
