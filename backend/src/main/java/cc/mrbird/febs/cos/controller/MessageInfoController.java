package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.MessageInfo;
import cc.mrbird.febs.cos.service.IMessageInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author FanK
 */
@RestController
@RequestMapping("/cos/message-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MessageInfoController {

    private final IMessageInfoService messageInfoService;

    /**
     * 分页查询消息信息
     *
     * @param page        分页对象
     * @param messageInfo 参数
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<MessageInfo> page, MessageInfo messageInfo) {
        return R.ok(messageInfoService.selectMessagePage(page, messageInfo));
    }

    /**
     * 根据用户获取未读消息
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return 结果
     */
    @GetMapping("/message/list")
    public R selectMessageList(@RequestParam("userId") Integer userId, @RequestParam("roleId") Integer roleId) {
        return R.ok(messageInfoService.selectMessageList(userId, roleId));
    }

    /**
     * 设置消息已读
     *
     * @param messageId 消息ID
     * @return 结果
     */
    @GetMapping("/setStatus/{messageId}")
    public R setStatus(@PathVariable("messageId") Integer messageId) {
        return R.ok(messageInfoService.update(Wrappers.<MessageInfo>lambdaUpdate().set(MessageInfo::getDelFlag, 1).eq(MessageInfo::getId, messageId)));
    }

    /**
     * 根据用户编号获取消息信息
     *
     * @param userCode 用户编号
     * @return 结果
     */
    @GetMapping("/list/user/{userCode}")
    public R list(@PathVariable("userCode") String userCode) {
        return R.ok(messageInfoService.selectMessageByUser(userCode));
    }

    /**
     * 添加消息信息
     *
     * @param messageInfo 消息信息
     * @return 结果
     */
    @PostMapping
    public R save(MessageInfo messageInfo) {
        messageInfo.setDelFlag("0");
        messageInfo.setCreateDate(DateUtil.formatDate(new Date()));
        return R.ok(messageInfoService.save(messageInfo));
    }

    /**
     * 修改消息信息
     *
     * @param messageInfo 消息信息
     * @return 结果
     */
    @PutMapping
    public R edit(MessageInfo messageInfo) {
        return R.ok(messageInfoService.updateById(messageInfo));
    }

    /**
     * 删除消息
     *
     * @param ids 消息IDS
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(messageInfoService.removeByIds(ids));
    }

}
