package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.RepairInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface RepairInfoMapper extends BaseMapper<RepairInfo> {

    /**
     * 分页获取维修信息
     *
     * @param page       分页对象
     * @param repairInfo 维修信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectRepairPage(Page<RepairInfo> page, @Param("repairInfo") RepairInfo repairInfo);

    /**
     * 根据时间获取维修单信息
     *
     * @return 结果
     */
    List<RepairInfo> selectRepairByDate();
}
