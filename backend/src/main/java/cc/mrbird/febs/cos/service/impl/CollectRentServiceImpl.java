package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.CollectRent;
import cc.mrbird.febs.cos.dao.CollectRentMapper;
import cc.mrbird.febs.cos.service.ICollectRentService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class CollectRentServiceImpl extends ServiceImpl<CollectRentMapper, CollectRent> implements ICollectRentService {

    /**
     * 分页获取收藏信息
     *
     * @param page 分页对象
     * @param collectRent 收藏信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectCollectPage(Page<CollectRent> page, CollectRent collectRent) {
        return baseMapper.selectCollectPage(page, collectRent);
    }
}
