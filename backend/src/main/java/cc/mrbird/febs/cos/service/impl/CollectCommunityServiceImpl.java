package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.CollectCommunity;
import cc.mrbird.febs.cos.dao.CollectCommunityMapper;
import cc.mrbird.febs.cos.service.ICollectCommunityService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class CollectCommunityServiceImpl extends ServiceImpl<CollectCommunityMapper, CollectCommunity> implements ICollectCommunityService {

    /**
     * 分页获取收藏信息
     *
     * @param page 分页对象
     * @param collectCommunity 收藏信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectCollectPage(Page<CollectCommunity> page, CollectCommunity collectCommunity) {
        return baseMapper.selectCollectPage(page, collectCommunity);
    }
}
