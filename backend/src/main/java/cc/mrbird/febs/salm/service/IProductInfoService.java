package cc.mrbird.febs.salm.service;

import cc.mrbird.febs.salm.entity.ProductInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IProductInfoService extends IService<ProductInfo> {

    /**
     * 分页获取产品信息
     *
     * @param page 分页对象
     * @param productInfo 产品信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectProductPage(Page<ProductInfo> page, ProductInfo productInfo);
}
