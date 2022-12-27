package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.RentEvaluation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface RentEvaluationMapper extends BaseMapper<RentEvaluation> {

    /**
     * 分页获取租房评价信息
     *
     * @param page 分页对象
     * @param rentEvaluation 租房评价信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectRentEvaluationPage(Page<RentEvaluation> page, @Param("rentEvaluation") RentEvaluation rentEvaluation);
}
