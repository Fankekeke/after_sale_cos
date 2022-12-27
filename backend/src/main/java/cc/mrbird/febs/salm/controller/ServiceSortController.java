package cc.mrbird.febs.salm.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.salm.entity.ServiceSort;
import cc.mrbird.febs.salm.service.IServiceSortService;
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
@RequestMapping("/manage/service-sort")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceSortController {

    private final IServiceSortService serviceSortService;

    /**
     * 分页获取服务类型信息
     *
     * @param page 分页对象
     * @param serviceSort 服务类型信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<ServiceSort> page, ServiceSort serviceSort) {
        return R.ok();
    }

    /**
     * 新增服务类型信息
     *
     * @param serviceSort 服务类型信息
     * @return 结果
     */
    @PostMapping
    public R save(ServiceSort serviceSort) {
        serviceSort.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(serviceSortService.save(serviceSort));
    }

    /**
     * 修改服务类型信息
     *
     * @param serviceSort 服务类型信息
     * @return 结果
     */
    @PutMapping
    public R edit(ServiceSort serviceSort) {
        return R.ok(serviceSortService.updateById(serviceSort));
    }

    /**
     * 删除服务类型信息
     *
     * @param ids ids
     * @return 服务类型信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(serviceSortService.removeByIds(ids));
    }

}
