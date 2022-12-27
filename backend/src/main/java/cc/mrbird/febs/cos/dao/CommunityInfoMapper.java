package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.CommunityInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface CommunityInfoMapper extends BaseMapper<CommunityInfo> {

    /**
     * 分页获取小区信息
     *
     * @param page 分页对象
     * @param communityInfo 小区信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectCommunityPage(Page<CommunityInfo> page, @Param("communityInfo") CommunityInfo communityInfo);

    /**
     * 远程获取小区信息
     *
     * @param name 小区信息
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> remoteCommunity(@Param("name") String name);

    /**
     * 根据小区编号获取租房信息
     *
     * @param code 小区编号
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectHouseRentByCode(@Param("code") String code);

}
