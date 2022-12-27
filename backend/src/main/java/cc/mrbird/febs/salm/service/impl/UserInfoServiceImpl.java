package cc.mrbird.febs.salm.service.impl;

import cc.mrbird.febs.salm.entity.UserInfo;
import cc.mrbird.febs.salm.dao.UserInfoMapper;
import cc.mrbird.febs.salm.service.IUserInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

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
}
