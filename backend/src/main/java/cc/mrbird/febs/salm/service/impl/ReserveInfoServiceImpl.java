package cc.mrbird.febs.salm.service.impl;

import cc.mrbird.febs.salm.entity.ReserveInfo;
import cc.mrbird.febs.salm.dao.ReserveInfoMapper;
import cc.mrbird.febs.salm.service.IReserveInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class ReserveInfoServiceImpl extends ServiceImpl<ReserveInfoMapper, ReserveInfo> implements IReserveInfoService {

    /**
     * 分页获取预约信息
     *
     * @param page 分页对象
     * @param reserveInfo 预约信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectReservePage(Page<ReserveInfo> page, ReserveInfo reserveInfo) {
        return baseMapper.selectReservePage(page, reserveInfo);
    }
}
