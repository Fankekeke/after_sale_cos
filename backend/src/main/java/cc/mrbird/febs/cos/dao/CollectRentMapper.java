package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.CollectRent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface CollectRentMapper extends BaseMapper<CollectRent> {

    /**
     * 分页获取收藏信息
     *
     * @param page 分页对象
     * @param collectRent 收藏信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectCollectPage(Page<CollectRent> page, @Param("collectRent") CollectRent collectRent);
}
