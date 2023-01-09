package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.OrderInfo;
import cc.mrbird.febs.cos.entity.UserInfo;
import cc.mrbird.febs.cos.service.IOrderInfoService;
import cc.mrbird.febs.cos.service.IUserInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
@RestController
@RequestMapping("/cos/user-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserInfoController {

    private final IUserInfoService userInfoService;

    private final IOrderInfoService orderInfoService;

    /**
     * 分页获取客户信息
     *
     * @param page     分页对象
     * @param userInfo 客户信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<UserInfo> page, UserInfo userInfo) {
        return R.ok(userInfoService.selectUserPage(page, userInfo));
    }

    /**
     * 用户信息详情
     *
     * @param userId 用户ID
     * @return 结果
     */
    @GetMapping("/detail/{userId}")
    public R detail(@PathVariable("userId") Long userId) {
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
        result.put("user", userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, userId)));
        UserInfo user = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, userId));
        // 用户工单
        List<OrderInfo> orderInfoList = orderInfoService.list(Wrappers.<OrderInfo>lambdaQuery().eq(OrderInfo::getCustomerId, user.getId()));
        result.put("order", orderInfoList);
        return R.ok(result);
    }

    /**
     * 更新客户状态
     *
     * @param userId 客户ID
     * @param status 状态
     * @return 结果
     */
    @PostMapping("/account/status")
    public R accountStatusEdit(@RequestParam("userId") Integer userId, @RequestParam("status") Integer status) throws Exception {
        return R.ok(userInfoService.accountStatusEdit(userId, status));
    }

    /**
     * 新增客户信息
     *
     * @param userInfo 客户信息
     * @return 结果
     */
    @PostMapping
    public R save(UserInfo userInfo) {
        userInfo.setCode("U-" + System.currentTimeMillis());
        userInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(userInfoService.save(userInfo));
    }

    /**
     * 修改客户信息
     *
     * @param userInfo 客户信息
     * @return 结果
     */
    @PutMapping
    public R edit(UserInfo userInfo) {
        return R.ok(userInfoService.updateById(userInfo));
    }

    /**
     * 删除客户信息
     *
     * @param ids ids
     * @return 客户信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(userInfoService.removeByIds(ids));
    }

}
