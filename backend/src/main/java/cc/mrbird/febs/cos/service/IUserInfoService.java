package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.UserInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface IUserInfoService extends IService<UserInfo> {

    /**
     * 分页查询用户信息
     *
     * @param page     分页对象
     * @param userInfo 用户信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> getUserInfoByPage(Page<UserInfo> page, UserInfo userInfo);

    /**
     * 远程调用用户信息
     *
     * @param userName 用户名称
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> remote(String userName);
}
