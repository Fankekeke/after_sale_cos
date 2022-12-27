package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.PostInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface IPostInfoService extends IService<PostInfo> {

    /**
     * 分页获取发帖记录信息
     *
     * @param page     分页对象
     * @param postInfo 发帖记录信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectPostPage(Page<PostInfo> page, PostInfo postInfo);

    /**
     * 根据贴子ID获取回复信息
     *
     * @param postId 贴子ID
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> replyInfoByPostId(Integer postId);

    /**
     * 获取贴子信息
     *
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> getPostList();

    /**
     * 根据贴子编号获取详细信息
     *
     * @param postId 贴子ID
     * @return 结果
     */
    LinkedHashMap<String, Object> getPostInfoById(Integer postId);

    /**
     * 获取热门贴子信息
     *
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> getPostListHot();
}
