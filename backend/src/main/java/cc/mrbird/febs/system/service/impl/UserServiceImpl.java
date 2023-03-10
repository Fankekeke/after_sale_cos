package cc.mrbird.febs.system.service.impl;

import cc.mrbird.febs.common.domain.FebsConstant;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.service.CacheService;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.common.utils.MD5Util;
import cc.mrbird.febs.cos.entity.StaffInfo;
import cc.mrbird.febs.cos.entity.UserInfo;
import cc.mrbird.febs.cos.service.IStaffInfoService;
import cc.mrbird.febs.cos.service.IUserInfoService;
import cc.mrbird.febs.system.dao.UserMapper;
import cc.mrbird.febs.system.dao.UserRoleMapper;
import cc.mrbird.febs.system.domain.User;
import cc.mrbird.febs.system.domain.UserRole;
import cc.mrbird.febs.system.manager.UserManager;
import cc.mrbird.febs.system.service.UserConfigService;
import cc.mrbird.febs.system.service.UserRoleService;
import cc.mrbird.febs.system.service.UserService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@Service("userService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserConfigService userConfigService;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private UserManager userManager;
    @Autowired
    private IStaffInfoService staffInfoService;
    @Autowired
    private IUserInfoService userInfoService;


    @Override
    public User findByName(String username) {
        return baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }


    @Override
    public IPage<User> findUserDetail(User user, QueryRequest request) {
        try {
            Page<User> page = new Page<>();
            SortUtil.handlePageSort(request, page, "userId", FebsConstant.ORDER_ASC, false);
            return this.baseMapper.findUserDetail(page, user);
        } catch (Exception e) {
            log.error("??????????????????", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void updateLoginTime(String username) throws Exception {
        User user = new User();
        user.setLastLoginTime(new Date());

        this.baseMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUsername, username));

        // ?????????????????????????????? redis???
        cacheService.saveUser(username);
    }

    @Override
    @Transactional
    public void createUser(User user) throws Exception {
        // ????????????
        user.setCreateTime(new Date());
        user.setAvatar(User.DEFAULT_AVATAR);
        user.setPassword(MD5Util.encrypt(user.getUsername(), User.DEFAULT_PASSWORD));
        save(user);

        // ??????????????????
        String[] roles = user.getRoleId().split(StringPool.COMMA);
        setUserRoles(user, roles);

        // ????????????????????????????????????
        userConfigService.initDefaultUserConfig(String.valueOf(user.getUserId()));

        // ?????????????????????????????? Redis???
        userManager.loadUserRedisCache(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) throws Exception {
        // ????????????
        user.setPassword(null);
        user.setModifyTime(new Date());
        updateById(user);

        userRoleMapper.delete(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, user.getUserId()));

        String[] roles = user.getRoleId().split(StringPool.COMMA);
        setUserRoles(user, roles);

        // ??????????????????????????????????????????????????????????????? ????????? redis???
        cacheService.saveUser(user.getUsername());
        cacheService.saveRoles(user.getUsername());
        cacheService.savePermissions(user.getUsername());
    }

    @Override
    @Transactional
    public void deleteUsers(String[] userIds) throws Exception {
        // ????????????????????????
        this.userManager.deleteUserRedisCache(userIds);

        List<String> list = Arrays.asList(userIds);

        removeByIds(list);

        // ??????????????????
        this.userRoleService.deleteUserRolesByUserId(userIds);
        // ???????????????????????????
        this.userConfigService.deleteByUserId(userIds);
    }

    @Override
    @Transactional
    public void updateProfile(User user) throws Exception {
        updateById(user);
        // ????????????????????????
        cacheService.saveUser(user.getUsername());
    }

    @Override
    @Transactional
    public void updateAvatar(String username, String avatar) throws Exception {
        User user = new User();
        user.setAvatar(avatar);

        this.baseMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        // ????????????????????????
        cacheService.saveUser(username);
    }

    @Override
    @Transactional
    public void updatePassword(String username, String password) throws Exception {
        User user = new User();
        user.setPassword(MD5Util.encrypt(username, password));

        this.baseMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        // ????????????????????????
        cacheService.saveUser(username);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void regist(String username, String password, String staffCode) throws Exception {
        StaffInfo staffInfo = staffInfoService.getOne(Wrappers.<StaffInfo>lambdaQuery().eq(StaffInfo::getCode, staffCode));
        if (staffInfo == null) {
            throw new FebsException("?????????????????????");
        }

        User user = new User();
        user.setPassword(MD5Util.encrypt(username, password));
        user.setUsername(username);
        user.setCreateTime(new Date());
        user.setStatus(User.STATUS_VALID);
        user.setSsex(User.SEX_UNKNOW);
        user.setAvatar(User.DEFAULT_AVATAR);
        user.setDescription("????????????");
        this.save(user);


        UserRole ur = new UserRole();
        ur.setUserId(user.getUserId());
        ur.setRoleId(75L); // ?????????????????? ID
        this.userRoleMapper.insert(ur);

        // ????????????????????????????????????
        userConfigService.initDefaultUserConfig(String.valueOf(user.getUserId()));
        // ?????????????????????????????? Redis???
        userManager.loadUserRedisCache(user);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registUser(String username, String password, String name) throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setName(name);
        userInfo.setCode("U-" + System.currentTimeMillis());
        userInfo.setStatus(1);
        userInfo.setCreateDate(DateUtil.formatDateTime(new Date()));

        User user = new User();
        user.setPassword(MD5Util.encrypt(username, password));
        user.setUsername(username);
        user.setCreateTime(new Date());
        user.setStatus(User.STATUS_VALID);
        user.setSsex(User.SEX_UNKNOW);
        user.setAvatar(User.DEFAULT_AVATAR);
        user.setDescription("????????????");
        this.save(user);
        userInfo.setUserId(user.getUserId());
        userInfoService.save(userInfo);

        UserRole ur = new UserRole();
        ur.setUserId(user.getUserId());
        ur.setRoleId(74L); // ?????????????????? ID
        this.userRoleMapper.insert(ur);

        // ????????????????????????????????????
        userConfigService.initDefaultUserConfig(String.valueOf(user.getUserId()));
        // ?????????????????????????????? Redis???
        userManager.loadUserRedisCache(user);
    }

    @Override
    @Transactional
    public void resetPassword(String[] usernames) throws Exception {
        for (String username : usernames) {

            User user = new User();
            user.setPassword(MD5Util.encrypt(username, User.DEFAULT_PASSWORD));

            this.baseMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUsername, username));
            // ?????????????????????????????? redis???
            cacheService.saveUser(username);
        }

    }

    private void setUserRoles(User user, String[] roles) {
        Arrays.stream(roles).forEach(roleId -> {
            UserRole ur = new UserRole();
            ur.setUserId(user.getUserId());
            ur.setRoleId(Long.valueOf(roleId));
            this.userRoleMapper.insert(ur);
        });
    }
}
