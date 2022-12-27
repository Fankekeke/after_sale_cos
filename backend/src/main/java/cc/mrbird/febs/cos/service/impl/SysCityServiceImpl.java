package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.SysCity;
import cc.mrbird.febs.cos.dao.SysCityMapper;
import cc.mrbird.febs.cos.service.ISysCityService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class SysCityServiceImpl extends ServiceImpl<SysCityMapper, SysCity> implements ISysCityService {

    /**
     * 分页查询城市信息
     *
     * @param page    分页对象
     * @param sysCity 城市信息
     * @return 城市信息列表
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> getCityByPage(Page<SysCity> page, SysCity sysCity) {
        return baseMapper.getCityByPage(page, sysCity);
    }
}
