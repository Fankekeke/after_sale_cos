package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.MessageRecord;
import cc.mrbird.febs.cos.dao.MessageRecordMapper;
import cc.mrbird.febs.cos.entity.MessageTemplate;
import cc.mrbird.febs.cos.service.IMailService;
import cc.mrbird.febs.cos.service.IMessageRecordService;
import cc.mrbird.febs.cos.service.IMessageTemplateService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MessageRecordServiceImpl extends ServiceImpl<MessageRecordMapper, MessageRecord> implements IMessageRecordService {

    private final IMessageTemplateService messageTemplateService;

    private final TemplateEngine templateEngine;

    private final IMailService mailService;

    /**
     * 分页查询消息发送记录
     *
     * @param page        分页对象
     * @param messageRecord 参数
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectMessageRecordPage(Page<MessageRecord> page, MessageRecord messageRecord) {
        return baseMapper.selectMessageRecordPage(page, messageRecord);
    }

    /**
     * 发送消息
     *
     * @param messageRecord 消息信息
     * @return 结果
     */
    @Override
    public boolean send(MessageRecord messageRecord) {
        MessageTemplate messageTemplate = messageTemplateService.getById(messageRecord.getTemplateId());
        if (StrUtil.isNotEmpty(messageRecord.getEmail())) {
            // 发送邮件
            Context context = new Context();
            context.setVariable("today", DateUtil.formatDate(new Date()));
            context.setVariable("custom", messageRecord.getUserName() + "，您好。" + messageTemplate.getContent());
            String emailContent = templateEngine.process("registerEmail", context);
            mailService.sendHtmlMail(messageRecord.getEmail(), messageTemplate.getTitle(), emailContent);
        }
        messageRecord.setCreateDate(DateUtil.formatDateTime(new Date()));
        return this.save(messageRecord);
    }
}
