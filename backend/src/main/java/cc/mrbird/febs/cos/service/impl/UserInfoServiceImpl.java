package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.common.service.CacheService;
import cc.mrbird.febs.cos.entity.StaffInfo;
import cc.mrbird.febs.cos.entity.UserInfo;
import cc.mrbird.febs.cos.dao.UserInfoMapper;
import cc.mrbird.febs.cos.service.IUserInfoService;
import cc.mrbird.febs.system.domain.User;
import cc.mrbird.febs.system.service.UserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    private final UserService userService;

    private final CacheService cacheService;

    /**
     * 分页获取客户信息
     *
     * @param page 分页对象
     * @param userInfo 客户信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectUserPage(Page<UserInfo> page, UserInfo userInfo) {
        return baseMapper.selectUserPage(page, userInfo);
    }

    /**
     * 更新客户状态
     *
     * @param userId 客户ID
     * @param status 状态
     * @return 结果
     */
    @Override
    public boolean accountStatusEdit(Integer userId, Integer status) throws Exception {
        // 获取员工信息
        UserInfo userInfo = this.getById(userId);
        // 获取账户信息
        User user = userService.getById(userInfo.getUserId());
        // 设置账户状态
        String accountStatus = status == 1 ? "1" : "0";
        user.setStatus(accountStatus);
        userInfo.setStatus(status);
        userService.updateById(user);
        // 重新将用户信息，用户角色信息，用户权限信息 加载到 redis中
        cacheService.saveUser(user.getUsername());
        cacheService.saveRoles(user.getUsername());
        cacheService.savePermissions(user.getUsername());
        return this.updateById(userInfo);
    }
}
