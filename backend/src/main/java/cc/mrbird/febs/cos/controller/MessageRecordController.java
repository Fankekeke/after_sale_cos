package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.MessageInfo;
import cc.mrbird.febs.cos.entity.MessageRecord;
import cc.mrbird.febs.cos.service.IMessageRecordService;
import cn.hutool.core.date.DateUtil;
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
@RequestMapping("/cos/message-record")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MessageRecordController {

    private final IMessageRecordService messageRecordService;

    /**
     * 分页查询消息发送记录
     *
     * @param page          分页对象
     * @param messageRecord 参数
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<MessageRecord> page, MessageRecord messageRecord) {
        return R.ok(messageRecordService.selectMessageRecordPage(page, messageRecord));
    }

    /**
     * 消息发送记录
     *
     * @param messageRecord 参数
     * @return 结果
     */
    @PostMapping
    public R send(MessageRecord messageRecord) {
        return R.ok(messageRecordService.send(messageRecord));
    }

    /**
     * 删除消息
     *
     * @param ids 消息IDS
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(messageRecordService.removeByIds(ids));
    }

}
