package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.MessageRecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IMessageRecordService extends IService<MessageRecord> {

    /**
     * 分页查询消息发送记录
     *
     * @param page          分页对象
     * @param messageRecord 参数
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectMessageRecordPage(Page<MessageRecord> page, MessageRecord messageRecord);

    /**
     * 发送消息
     *
     * @param messageRecord 消息信息
     * @return 结果
     */
    boolean send(MessageRecord messageRecord);

}
