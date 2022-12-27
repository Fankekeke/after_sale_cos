package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.MessageInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface MessageInfoMapper extends BaseMapper<MessageInfo> {

    /**
     * 分页查询消息信息
     *
     * @param page        分页对象
     * @param messageInfo 参数
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectMessagePage(Page<MessageInfo> page, @Param("messageInfo") MessageInfo messageInfo);

    /**
     * 根据用户编号获取消息信息
     *
     * @param userCode 用户编号
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectMessageByUser(@Param("userCode") String userCode);

    /**
     * 根据用户编号获取消息信息
     *
     * @param userCode 用户编号
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectMessageList(@Param("userCode") String userCode);
}
