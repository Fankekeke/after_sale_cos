package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.ProductInfo;
import cc.mrbird.febs.cos.dao.ProductInfoMapper;
import cc.mrbird.febs.cos.service.IProductInfoService;
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
