package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.BulletinInfo;
import cc.mrbird.febs.cos.service.IBulletinInfoService;
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
@RequestMapping("/cos/bulletin-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BulletinInfoController {

    private final IBulletinInfoService bulletinInfoService;

    /**
     * 分页获取公告信息
     *
     * @param page 分页对象
     * @param bulletinInfo 公告信息
     * @return 结果
     */
    @ApiOperation(value = "分页获取公告信息", notes = "分页获取公告信息")
    @ApiImplicitParam(name = "bulletinInfo", value = "公告信息", required = true, dataType = "BulletinInfo")
    @GetMapping("/page")
    public R page(Page<BulletinInfo> page, BulletinInfo bulletinInfo) {
        return R.ok(bulletinInfoService.getBulletinByPage(page, bulletinInfo));
    }

    @ApiOperation(value = "获取公告信息详情", notes = "获取公告信息详情")
    @ApiImplicitParam(name = "id", value = "公告信息ID", required = true, dataType = "Integer")
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(bulletinInfoService.getById(id));
    }

    @ApiOperation(value = "获取公告信息列表", notes = "获取公告信息列表")
    @GetMapping("/list")
    public R list() {
        return R.ok(bulletinInfoService.list());
    }

    /**
     * 新增公告信息
     *
     * @param bulletinInfo 公告信息
     * @return 结果
     */
    @ApiOperation(value = "新增公告信息", notes = "新增公告信息")
    @ApiImplicitParam(name = "bulletinInfo", value = "公告信息", required = true, dataType = "BulletinInfo")
    @PostMapping
    public R save(BulletinInfo bulletinInfo) {
        bulletinInfo.setDate(DateUtil.formatDateTime(new Date()));
        return R.ok(bulletinInfoService.save(bulletinInfo));
    }

    /**
     * 修改公告信息
     *
     * @param bulletinInfo 公告信息
     * @return 结果
     */
    @ApiOperation(value = "修改公告信息", notes = "修改公告信息")
    @ApiImplicitParam(name = "bulletinInfo", value = "公告信息", required = true, dataType = "BulletinInfo")
    @PutMapping
    public R edit(BulletinInfo bulletinInfo) {
        return R.ok(bulletinInfoService.updateById(bulletinInfo));
    }

    /**
     * 删除公告信息
     *
     * @param ids ids
     * @return 公告信息
     */
    @ApiOperation(value = "删除公告信息", notes = "删除公告信息")
    @ApiImplicitParam(name = "ids", value = "公告信息ids", required = true, dataType = "List")
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(bulletinInfoService.removeByIds(ids));
    }
}
