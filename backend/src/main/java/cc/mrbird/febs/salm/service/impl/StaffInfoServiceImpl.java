package cc.mrbird.febs.salm.service.impl;

import cc.mrbird.febs.salm.entity.StaffInfo;
import cc.mrbird.febs.salm.dao.StaffInfoMapper;
import cc.mrbird.febs.salm.service.IStaffInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class StaffInfoServiceImpl extends ServiceImpl<StaffInfoMapper, StaffInfo> implements IStaffInfoService {

    /**
     * 分页获取员工信息
     *
     * @param page 分页对象
     * @param staffInfo 员工信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectStaffPage(Page<StaffInfo> page, StaffInfo staffInfo) {
        return baseMapper.selectStaffPage(page, staffInfo);
    }
}
