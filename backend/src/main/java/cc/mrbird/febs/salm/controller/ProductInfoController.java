package cc.mrbird.febs.salm.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.salm.entity.ProductInfo;
import cc.mrbird.febs.salm.service.IProductInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author FanK
 */
@RestController
@RequestMapping("/manage/product-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductInfoController {

    private final IProductInfoService productInfoService;

    /**
     * 分页获取产品信息
     *
     * @param page 分页对象
     * @param productInfo 产品信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<ProductInfo> page, ProductInfo productInfo) {
        return R.ok();
    }

    /**
     * 新增产品信息
     *
     * @param productInfo 产品信息
     * @return 结果
     */
    @PostMapping
    public R save(ProductInfo productInfo) {
        productInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(productInfoService.save(productInfo));
    }

    /**
     * 修改产品信息
     *
     * @param productInfo 产品信息
     * @return 结果
     */
    @PutMapping
    public R edit(ProductInfo productInfo) {
        return R.ok(productInfoService.updateById(productInfo));
    }

    /**
     * 删除产品信息
     *
     * @param ids ids
     * @return 产品信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(productInfoService.removeByIds(ids));
    }

}
