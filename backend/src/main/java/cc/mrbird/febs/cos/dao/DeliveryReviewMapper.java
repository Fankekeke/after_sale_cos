package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.DeliveryReview;
import cc.mrbird.febs.cos.entity.vo.ContractVo;
import cc.mrbird.febs.cos.entity.vo.DeliveryReviewVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface DeliveryReviewMapper extends BaseMapper<DeliveryReview> {

    /**
     * 分页获取交付审核信息
     *
     * @param page           分页对象
     * @param deliveryReview 参数
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectDeliveryPage(Page<DeliveryReview> page, @Param("deliveryReview") DeliveryReview deliveryReview);

    /**
     * 获取正常合同信息
     *
     * @param userCode 用户编号
     * @return 结果
     */
    List<DeliveryReviewVo> selectDeliveryOver(@Param("userCode") String userCode);

    /**
     * 导出合同信息
     *
     * @param contractCode 合同编号
     * @return 结果
     */
    ContractVo exportContract(@Param("contractCode") String contractCode);
}
