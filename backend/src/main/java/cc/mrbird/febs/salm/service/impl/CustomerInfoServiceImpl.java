package cc.mrbird.febs.salm.service.impl;

import cc.mrbird.febs.salm.entity.CustomerInfo;
import cc.mrbird.febs.salm.dao.CustomerInfoMapper;
import cc.mrbird.febs.salm.service.ICustomerInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author FanK
 */
@Service
public class CustomerInfoServiceImpl extends ServiceImpl<CustomerInfoMapper, CustomerInfo> implements ICustomerInfoService {

}
