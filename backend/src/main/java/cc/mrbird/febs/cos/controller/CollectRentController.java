package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.CollectRent;
import cc.mrbird.febs.cos.service.ICollectRentService;
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
@RequestMapping("/cos/collect-rent")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CollectRentController {
    
    private final ICollectRentService collectRentService;

    /**
     * 分页获取收藏信息
     *
     * @param page 分页对象
     * @param collectRent 收藏信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<CollectRent> page, CollectRent collectRent) {
        return R.ok(collectRentService.selectCollectPage(page, collectRent));
    }

    /**
     * 新增收藏信息
     *
     * @param collectRent 收藏信息
     * @return 结果
     */
    @PostMapping
    public R save(CollectRent collectRent) {
        collectRent.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(collectRentService.save(collectRent));
    }

    /**
     * 删除收藏信息
     *
     * @param ids ids
     * @return 收藏信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(collectRentService.removeByIds(ids));
    }

}
