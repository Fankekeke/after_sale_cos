package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.PostInfo;
import cc.mrbird.febs.cos.dao.PostInfoMapper;
import cc.mrbird.febs.cos.service.IPostInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
@Service
public class PostInfoServiceImpl extends ServiceImpl<PostInfoMapper, PostInfo> implements IPostInfoService {

    /**
     * 分页获取发帖记录信息
     *
     * @param page 分页对象
     * @param postInfo 发帖记录信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectPostPage(Page<PostInfo> page, PostInfo postInfo) {
        return baseMapper.selectPostPage(page, postInfo);
    }

    /**
     * 根据贴子ID获取回复信息
     *
     * @param postId 贴子ID
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> replyInfoByPostId(Integer postId) {
        return baseMapper.replyInfoByPostId(postId);
    }

    /**
     * 获取贴子信息
     *
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> getPostList() {
        return baseMapper.getPostList();
    }

    /**
     * 根据贴子编号获取详细信息
     *
     * @param postId 贴子ID
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> getPostInfoById(Integer postId) {
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        result.put("postInfo", baseMapper.getPostInfoById(postId));
        result.put("replyInfo", baseMapper.replyInfoByPostId(postId));
        return result;
    }

    /**
     * 获取热门贴子信息
     *
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> getPostListHot() {
        return baseMapper.getPostListHot();
    }

}
