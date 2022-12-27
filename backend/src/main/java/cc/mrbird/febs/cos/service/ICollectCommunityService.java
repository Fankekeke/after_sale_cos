package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.CollectCommunity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface ICollectCommunityService extends IService<CollectCommunity> {

    /**
     * 分页获取收藏信息
     *
     * @param page 分页对象
     * @param collectCommunity 收藏信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectCollectPage(Page<CollectCommunity> page, CollectCommunity collectCommunity);
}
