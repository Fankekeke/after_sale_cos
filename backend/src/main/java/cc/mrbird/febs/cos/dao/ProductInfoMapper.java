package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.ProductInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface ProductInfoMapper extends BaseMapper<ProductInfo> {

    /**
     * 分页获取产品信息
     *
     * @param page 分页对象
     * @param productInfo 产品信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectProductPage(Page<ProductInfo> page, @Param("productInfo") ProductInfo productInfo);
}
