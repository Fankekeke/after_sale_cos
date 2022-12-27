package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.MessageInfo;
import cc.mrbird.febs.cos.dao.MessageInfoMapper;
import cc.mrbird.febs.cos.entity.UserInfo;
import cc.mrbird.febs.cos.service.IMessageInfoService;
import cc.mrbird.febs.cos.service.IUserInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MessageInfoServiceImpl extends ServiceImpl<MessageInfoMapper, MessageInfo> implements IMessageInfoService {

    private final IUserInfoService userInfoService;

    /**
     * 分页查询消息信息
     *
     * @param page        分页对象
     * @param messageInfo 参数
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectMessagePage(Page<MessageInfo> page, MessageInfo messageInfo) {
        return baseMapper.selectMessagePage(page, messageInfo);
    }

    /**
     * 根据用户编号获取消息信息
     *
     * @param userCode 用户编号
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectMessageByUser(String userCode) {
        return baseMapper.selectMessageByUser(userCode);
    }

    /**
     * 根据用户获取未读消息
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectMessageList(Integer userId, Integer roleId) {
        if (roleId == 74) {
            return baseMapper.selectMessageList(null);
        }
        if (roleId == 76) {
            UserInfo userInfo = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getSysUserId, userId));
            if (userInfo == null) {
                return Collections.emptyList();
            } else {
                return baseMapper.selectMessageList(userInfo.getCode());
            }
        }
        return Collections.emptyList();
    }
}
