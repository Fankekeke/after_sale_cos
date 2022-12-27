package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.SysCity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface ISysCityService extends IService<SysCity> {

    /**
     * 分页查询城市信息
     *
     * @param page    分页对象
     * @param sysCity 城市信息
     * @return 城市信息列表
     */
    IPage<LinkedHashMap<String, Object>> getCityByPage(Page<SysCity> page, SysCity sysCity);
}
