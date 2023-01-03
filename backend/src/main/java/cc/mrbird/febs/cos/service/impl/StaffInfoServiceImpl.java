package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.common.service.CacheService;
import cc.mrbird.febs.cos.dao.RepairInfoMapper;
import cc.mrbird.febs.cos.entity.RepairInfo;
import cc.mrbird.febs.cos.entity.StaffInfo;
import cc.mrbird.febs.cos.dao.StaffInfoMapper;
import cc.mrbird.febs.cos.entity.vo.RepairVo;
import cc.mrbird.febs.cos.service.IStaffInfoService;
import cc.mrbird.febs.system.domain.User;
import cc.mrbird.febs.system.service.UserService;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StaffInfoServiceImpl extends ServiceImpl<StaffInfoMapper, StaffInfo> implements IStaffInfoService {

    private final RepairInfoMapper repairInfoMapper;

    private final UserService userService;

    private final CacheService cacheService;

    /**
     * 分页获取员工信息
     *
     * @param page 分页对象
     * @param staffInfo 员工信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectStaffPage(Page<StaffInfo> page, StaffInfo staffInfo) {
        return baseMapper.selectStaffPage(page, staffInfo);
    }

    /**
     * 查询员工工作状态
     *
     * @param day 时间
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectStaffWorkStatus(String day) {
        List<LinkedHashMap<String, Object>> result = new ArrayList<>();
        // 所有员工信息
        List<StaffInfo> staffInfoList = this.list(Wrappers.<StaffInfo>lambdaQuery().eq(StaffInfo::getStatus, 1));
        if (CollectionUtil.isEmpty(staffInfoList)) {
            return Collections.emptyList();
        }
        // 根据时间获取维修单信息
        List<RepairInfo> repairList = repairInfoMapper.selectRepairByDate();
        Map<Integer, List<RepairInfo>> repairStaffMap = repairList.stream().collect(Collectors.groupingBy(RepairInfo::getStaffId));
        staffInfoList.forEach(e -> {
            boolean ex = repairStaffMap == null;
            LinkedHashMap<String, Object> item = new LinkedHashMap<String, Object>() {
                {
                    put("id", e.getId());
                    put("name", e.getName());
                    put("status", !ex && CollectionUtil.isNotEmpty(repairStaffMap.get(e.getId())));
                }
            };
            result.add(item);
        });
        return result;
    }

    /**
     * 新增员工信息
     *
     * @param staffInfo 员工信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveStaff(StaffInfo staffInfo) throws Exception {
        // 设置员工编号
        staffInfo.setCode("SF-" + System.currentTimeMillis());
        staffInfo.setStatus(1);
        User user = new User();
        user.setUsername(staffInfo.getCode());
        user.setStatus("1");
        user.setRoleId("75");
        // 添加维修员账户
        userService.createUser(user);
        staffInfo.setUserId(user.getUserId());
        return this.save(staffInfo);
    }

    /**
     * 更新员工状态
     *
     * @param staffId 员工ID
     * @param status  状态
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean accountStatusEdit(Integer staffId, Integer status) throws Exception {
        // 获取员工信息
        StaffInfo staffInfo = this.getById(staffId);
        // 获取账户信息
        User user = userService.getById(staffInfo.getUserId());
        // 设置账户状态
        String accountStatus = status == 1 ? "1" : "0";
        user.setStatus(accountStatus);
        staffInfo.setStatus(status);
        userService.updateById(user);
        // 重新将用户信息，用户角色信息，用户权限信息 加载到 redis中
        cacheService.saveUser(user.getUsername());
        cacheService.saveRoles(user.getUsername());
        cacheService.savePermissions(user.getUsername());
        return this.updateById(staffInfo);
    }

    /**
     * 获取员工工作情况
     *
     * @param productId 产品ID
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectStaffWork(Integer productId) {
        return repairInfoMapper.selectStaffWork(productId);
    }

    /**
     * 员工评价统计
     *
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectStaffWorkStatus() {
        // 员工信息
        List<StaffInfo> staffInfoList = this.list(Wrappers.<StaffInfo>lambdaQuery().eq(StaffInfo::getStatus, 1));
        if (CollectionUtil.isEmpty(staffInfoList)) {
            return Collections.emptyList();
        }
        // 查询所有维修信息
        List<Integer> workStatusList = new ArrayList<Integer>(Arrays.asList(0, 1, 2));
        List<RepairVo> repairInfoList = baseMapper.selectRepairByStatus(workStatusList);
        // 按员工转MAP
        Map<Integer, List<RepairVo>> repairInfoMap = repairInfoList.stream().collect(Collectors.groupingBy(RepairVo::getStaffId));
        // 返回数据
        List<LinkedHashMap<String, Object>> result = new ArrayList<>();
        staffInfoList.forEach(e -> {
            List<RepairVo> repairVoList = repairInfoMap.get(e.getId());
            LinkedHashMap<String, Object> repairItem = new LinkedHashMap<String, Object>() {
                {
                    put("staff", e);
                }
            };
            if (CollectionUtil.isNotEmpty(repairVoList)) {
                repairItem.put("repair", repairInfoList);
            }
            result.add(repairItem);
        });
        return result;
    }
}
