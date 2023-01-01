package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.UserInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IUserInfoService extends IService<UserInfo> {

    /**
     * 分页获取客户信息
     *
     * @param page 分页对象
     * @param userInfo 客户信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectUserPage(Page<UserInfo> page, UserInfo userInfo);

    /**
     * 更新客户状态
     *
     * @param userId 客户ID
     * @param status 状态
     * @return 结果
     */
    boolean accountStatusEdit(Integer userId, Integer status) throws Exception;
}
