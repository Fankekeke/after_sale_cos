package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.CustomerInfo;
import cc.mrbird.febs.cos.dao.CustomerInfoMapper;
import cc.mrbird.febs.cos.service.ICustomerInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author FanK
 */
@Service
public class CustomerInfoServiceImpl extends ServiceImpl<CustomerInfoMapper, CustomerInfo> implements ICustomerInfoService {

}
