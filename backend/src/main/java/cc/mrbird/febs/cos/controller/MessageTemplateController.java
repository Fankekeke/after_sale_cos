package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.MessageInfo;
import cc.mrbird.febs.cos.entity.MessageRecord;
import cc.mrbird.febs.cos.entity.MessageTemplate;
import cc.mrbird.febs.cos.service.IMessageTemplateService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
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
@RequestMapping("/cos/message-template")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MessageTemplateController {

    private final IMessageTemplateService messageTemplateService;

    /**
     * 分页查询消息发送记录
     *
     * @param page            分页对象
     * @param messageTemplate 参数
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<MessageTemplate> page, MessageTemplate messageTemplate) {
        return R.ok(messageTemplateService.page(page, Wrappers.<MessageTemplate>lambdaQuery()
                .like(StrUtil.isNotEmpty(messageTemplate.getTitle()), MessageTemplate::getTitle, messageTemplate.getTitle())
                .like(StrUtil.isNotEmpty(messageTemplate.getContent()), MessageTemplate::getContent, messageTemplate.getContent())));
    }

    /**
     * 查询消息模板
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(messageTemplateService.list());
    }

    /**
     * 添加消息信息
     *
     * @param messageTemplate 消息信息
     * @return 结果
     */
    @PostMapping
    public R save(MessageTemplate messageTemplate) {
        messageTemplate.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(messageTemplateService.save(messageTemplate));
    }

    /**
     * 修改消息信息
     *
     * @param messageTemplate 消息信息
     * @return 结果
     */
    @PutMapping
    public R edit(MessageTemplate messageTemplate) {
        return R.ok(messageTemplateService.updateById(messageTemplate));
    }

    /**
     * 删除消息
     *
     * @param ids 消息IDS
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(messageTemplateService.removeByIds(ids));
    }

}
