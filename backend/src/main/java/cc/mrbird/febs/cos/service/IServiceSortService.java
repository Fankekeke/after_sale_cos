package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.ServiceSort;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IServiceSortService extends IService<ServiceSort> {

    /**
     * 分页获取服务类型信息
     *
     * @param page 分页对象
     * @param serviceSort 服务类型信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectServiceSortPage(Page<ServiceSort> page, ServiceSort serviceSort);
}
