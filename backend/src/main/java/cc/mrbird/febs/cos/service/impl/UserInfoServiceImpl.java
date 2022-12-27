package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.UserInfo;
import cc.mrbird.febs.cos.dao.UserInfoMapper;
import cc.mrbird.febs.cos.service.IUserInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    /**
     * 分页查询用户信息
     *
     * @param page     分页对象
     * @param userInfo 用户信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> getUserInfoByPage(Page<UserInfo> page, UserInfo userInfo) {
        return baseMapper.getUserInfoByPage(page, userInfo);
    }

    /**
     * 远程调用用户信息
     *
     * @param userName 用户名称
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> remote(String userName) {
        return baseMapper.remote(userName);
    }
}
