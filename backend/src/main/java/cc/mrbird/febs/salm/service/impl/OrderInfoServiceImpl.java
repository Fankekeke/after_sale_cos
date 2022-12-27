package cc.mrbird.febs.salm.service.impl;

import cc.mrbird.febs.salm.entity.OrderInfo;
import cc.mrbird.febs.salm.dao.OrderInfoMapper;
import cc.mrbird.febs.salm.service.IOrderInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {

    /**
     * 分页获取工单信息
     *
     * @param page 分页对象
     * @param orderInfo 工单信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectOrderPage(Page<OrderInfo> page, OrderInfo orderInfo) {
        return baseMapper.selectOrderPage(page, orderInfo);
    }
}
