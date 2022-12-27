package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.domain.Tree;
import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.common.utils.TreeUtil;
import cc.mrbird.febs.cos.entity.SysCity;
import cc.mrbird.febs.cos.service.ISysCityService;
import cc.mrbird.febs.system.domain.Dept;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author FanK
 */
@RestController
@RequestMapping("/cos/sys-city")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysCityController {

    private final ISysCityService sysCityService;

    /**
     * 查询城市信息
     *
     * @param page    分页对象
     * @param sysCity 城市信息
     * @return 城市信息列表
     */
    @GetMapping("/page")
    public R page(Page<SysCity> page, SysCity sysCity) {
        return R.ok(sysCityService.getCityByPage(page, sysCity));
    }

    /**
     * 根据ID获取城市
     *
     * @param id 城市Id
     * @return 城市信息
     */
    @GetMapping("/{id}")
    public R cityById(@PathVariable Integer id) {
        return R.ok(sysCityService.getById(id));
    }

    /**
     * 城市树
     *
     * @return 结果
     */
    @GetMapping("/cityChild")
    public R cityChild() {
        List<SysCity> cityList = sysCityService.list();
        List<Tree<SysCity>> trees = new ArrayList<>();
        cityList.forEach(city -> {
            Tree<SysCity> tree = new Tree<>();
            tree.setId(city.getId().toString());
            tree.setKey(tree.getId());
            tree.setParentId(city.getParentId().toString());
            tree.setText(city.getName());
            tree.setTitle(tree.getText());
            tree.setValue(tree.getId());
            trees.add(tree);
        });
        return R.ok(TreeUtil.build(trees));
    }

    /**
     * 获取省份信息
     *
     * @return 结果
     */
    @GetMapping("/province")
    public R selectProvince() {
        return R.ok(sysCityService.list(Wrappers.<SysCity>lambdaQuery().eq(SysCity::getParentId, 0)));
    }

    /**
     * 获取子集城市
     *
     * @param id id
     * @return 结果
     */
    @GetMapping("/city/{id}")
    public R selectCityByParentId(@PathVariable Integer id) {
        return R.ok(sysCityService.list(Wrappers.<SysCity>lambdaQuery().eq(SysCity::getParentId, id)));
    }

    /**
     * 城市数据联想
     *
     * @param key 关键字
     * @return 城市信息列表
     */
    @GetMapping("/united/{key}")
    public R cityUnited(@PathVariable("key") String key) {
        return R.ok(sysCityService.list(Wrappers.<SysCity>lambdaQuery().like(SysCity::getName, key).last("limit 10")));
    }

    /**
     * 新增城市信息
     *
     * @param sysCity 城市信息
     * @return 返回结果
     */
    @PostMapping
    public R save(SysCity sysCity) {
        return R.ok(sysCityService.save(sysCity));
    }

    /**
     * 修改城市信息
     *
     * @param sysCity 城市信息
     * @return 返回结果
     */
    @PutMapping
    public R edit(SysCity sysCity) {
        return R.ok(sysCityService.updateById(sysCity));
    }

    /**
     * 删除城市信息
     *
     * @param ids 城市Id
     * @return 返回结果
     */
    @DeleteMapping("/{ids}")
    public R delete(@PathVariable("ids") List<Integer> ids) {
        return R.ok(sysCityService.removeByIds(ids));
    }
}
