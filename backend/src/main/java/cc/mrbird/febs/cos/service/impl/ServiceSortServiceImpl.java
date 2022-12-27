package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.ServiceSort;
import cc.mrbird.febs.cos.dao.ServiceSortMapper;
import cc.mrbird.febs.cos.service.IServiceSortService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class ServiceSortServiceImpl extends ServiceImpl<ServiceSortMapper, ServiceSort> implements IServiceSortService {

    /**
     * 分页获取服务类型信息
     *
     * @param page 分页对象
     * @param serviceSort 服务类型信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectServiceSortPage(Page<ServiceSort> page, ServiceSort serviceSort) {
        return baseMapper.selectServiceSortPage(page, serviceSort);
    }
}
