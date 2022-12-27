package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.MessageRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface MessageRecordMapper extends BaseMapper<MessageRecord> {

    /**
     * 分页查询消息发送记录
     *
     * @param page        分页对象
     * @param messageRecord 参数
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectMessageRecordPage(Page<MessageRecord> page, @Param("messageRecord") MessageRecord messageRecord);
}
