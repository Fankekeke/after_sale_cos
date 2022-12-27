package cc.mrbird.febs.salm.service.impl;

import cc.mrbird.febs.salm.entity.ProductInfo;
import cc.mrbird.febs.salm.dao.ProductInfoMapper;
import cc.mrbird.febs.salm.service.IProductInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class ProductInfoServiceImpl extends ServiceImpl<ProductInfoMapper, ProductInfo> implements IProductInfoService {

    /**
     * 分页获取产品信息
     *
     * @param page 分页对象
     * @param productInfo 产品信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectProductPage(Page<ProductInfo> page, ProductInfo productInfo) {
        return baseMapper.selectProductPage(page, productInfo);
    }
}
