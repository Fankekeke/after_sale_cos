package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.PostInfo;
import cc.mrbird.febs.cos.service.IPostInfoService;
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
@RequestMapping("/cos/post-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PostInfoController {

    private final IPostInfoService postInfoService;

    /**
     * 分页获取发帖记录信息
     *
     * @param page     分页对象
     * @param postInfo 发帖记录信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<PostInfo> page, PostInfo postInfo) {
        return R.ok(postInfoService.selectPostPage(page, postInfo));
    }

    /**
     * 修改帖子状态
     *
     * @param postId 帖子ID
     * @param status 状态
     * @return 结果
     */
    @GetMapping("/setStatus")
    public R updatePostStatus(@RequestParam Integer postId, @RequestParam Integer status) {
        return R.ok(postInfoService.update(Wrappers.<PostInfo>lambdaUpdate().set(PostInfo::getDelFlag, status).eq(PostInfo::getId, postId)));
    }

    /**
     * 根据贴子ID获取回复信息
     *
     * @param postId 帖子ID
     * @return 结果
     */
    @GetMapping("/reply")
    public R replyInfoByPostId(@RequestParam Integer postId) {
        return R.ok(postInfoService.replyInfoByPostId(postId));
    }

    /**
     * 新增发帖记录信息
     *
     * @param postInfo 发帖记录信息
     * @return 结果
     */
    @PostMapping
    public R save(PostInfo postInfo) {
        postInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        postInfo.setDelFlag(0);
        return R.ok(postInfoService.save(postInfo));
    }

    /**
     * 修改发帖记录信息
     *
     * @param postInfo 发帖记录信息
     * @return 结果
     */
    @PutMapping
    public R edit(PostInfo postInfo) {
        return R.ok(postInfoService.updateById(postInfo));
    }

    /**
     * 删除发帖记录信息
     *
     * @param ids ids
     * @return 发帖记录信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(postInfoService.removeByIds(ids));
    }

}
