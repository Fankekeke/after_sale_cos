package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.CustomerInfo;
import cc.mrbird.febs.cos.service.ICustomerInfoService;
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
@RequestMapping("/cos/customer-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerInfoController {

    private final ICustomerInfoService customerInfoService;

    /**
     * 分页获取客户信息
     *
     * @param page 分页对象
     * @param customerInfo 客户信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<CustomerInfo> page, CustomerInfo customerInfo) {
        return R.ok();
    }

    /**
     * 新增客户信息
     *
     * @param customerInfo 客户信息
     * @return 结果
     */
    @PostMapping
    public R save(CustomerInfo customerInfo) {
        customerInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(customerInfoService.save(customerInfo));
    }

    /**
     * 修改客户信息
     *
     * @param customerInfo 客户信息
     * @return 结果
     */
    @PutMapping
    public R edit(CustomerInfo customerInfo) {
        return R.ok(customerInfoService.updateById(customerInfo));
    }

    /**
     * 删除客户信息
     *
     * @param ids ids
     * @return 客户信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(customerInfoService.removeByIds(ids));
    }

}
