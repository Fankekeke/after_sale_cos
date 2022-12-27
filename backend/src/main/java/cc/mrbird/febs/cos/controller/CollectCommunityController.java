package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.CollectCommunity;
import cc.mrbird.febs.cos.service.ICollectCommunityService;
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
@RequestMapping("/cos/collect-community")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CollectCommunityController {

    private final ICollectCommunityService collectCommunityService;

    /**
     * 分页获取收藏信息
     *
     * @param page 分页对象
     * @param collectCommunity 收藏信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<CollectCommunity> page, CollectCommunity collectCommunity) {
        return R.ok(collectCommunityService.selectCollectPage(page, collectCommunity));
    }

    /**
     * 新增收藏信息
     *
     * @param collectCommunity 收藏信息
     * @return 结果
     */
    @PostMapping
    public R save(CollectCommunity collectCommunity) {
        collectCommunity.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(collectCommunityService.save(collectCommunity));
    }

    /**
     * 删除收藏信息
     *
     * @param ids ids
     * @return 收藏信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(collectCommunityService.removeByIds(ids));
    }

}
