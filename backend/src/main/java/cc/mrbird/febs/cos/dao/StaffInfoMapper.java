package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.StaffInfo;
import cc.mrbird.febs.cos.entity.vo.RepairVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface StaffInfoMapper extends BaseMapper<StaffInfo> {

    /**
     * 分页获取员工信息
     *
     * @param page      分页对象
     * @param staffInfo 员工信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectStaffPage(Page<StaffInfo> page, @Param("staffInfo") StaffInfo staffInfo);

    /**
     * 根据状态获取维修信息
     *
     * @param statusList 维修状态
     * @return 结果
     */
    List<RepairVo> selectRepairByStatus(@Param("statusList") List<Integer> statusList);
}
