package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.MessageInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface IMessageInfoService extends IService<MessageInfo> {

    /**
     * 分页查询消息信息
     *
     * @param page        分页对象
     * @param messageInfo 参数
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectMessagePage(Page<MessageInfo> page, MessageInfo messageInfo);

    /**
     * 根据用户编号获取消息信息
     *
     * @param userCode 用户编号
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectMessageByUser(String userCode);

    /**
     * 根据用户获取未读消息
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectMessageList(Integer userId, Integer roleId);
}
