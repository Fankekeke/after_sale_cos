package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.dao.PaymentRecordMapper;
import cc.mrbird.febs.cos.entity.PaymentRecord;
import cc.mrbird.febs.cos.entity.RentCharge;
import cc.mrbird.febs.cos.entity.RentInfo;
import cc.mrbird.febs.cos.dao.RentInfoMapper;
import cc.mrbird.febs.cos.service.IRentChargeService;
import cc.mrbird.febs.cos.service.IRentInfoService;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RentInfoServiceImpl extends ServiceImpl<RentInfoMapper, RentInfo> implements IRentInfoService {

    private final IRentChargeService rentChargeService;

    private final PaymentRecordMapper paymentRecordMapper;

    private final RentInfoMapper rentInfoMapper;

    /**
     * 分页获取出租信息信息
     *
     * @param page 分页对象
     * @param rentInfo 出租信息信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectRentPage(Page<RentInfo> page, RentInfo rentInfo) {
        return baseMapper.selectRentPage(page, rentInfo);
    }

    /**
     * 获取租房状态
     *
     * @param rentId 租房ID
     * @return 结果
     */
    @Override
    public RentCharge selectRentStatus(String rentId) {
        return rentChargeService.getOne(Wrappers.<RentCharge>lambdaQuery().eq(RentCharge::getRentId, rentId));
    }

    /**
     * 获取首页数据
     *
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> selectHomeData() {
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
        // 本月收入
        result.put("monthAmount", paymentRecordMapper.selectAmount());
        // 新添房源
        result.put("monthRent", rentInfoMapper.selectRentCountByMonth());
        // 租出房源
        result.put("monthRentOut", paymentRecordMapper.selectRentStartByMonth());
        // 总收益
        BigDecimal all = paymentRecordMapper.selectList(Wrappers.<PaymentRecord>lambdaQuery()).stream().map(PaymentRecord::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        result.put("allAmount", all);
        // 近十天收入统计
        List<LinkedHashMap<String, Object>> rentPayment = paymentRecordMapper.selectLastRentPayment();
        result.put("rentPayment", rentPayment);
        // 近十天房源省份统计
        List<String> list = rentInfoMapper.selectProvinceList();
        LinkedHashMap<String, Object> provinceRent = new LinkedHashMap<>();
        if (CollectionUtil.isNotEmpty(list)) {
            list.forEach(item -> provinceRent.put(item, rentInfoMapper.selectRentProvince(item)));
            result.put("provinceRent", provinceRent);
        } else {
            result.put("provinceRent", null);
        }
        // 近十天房屋合租类型统计
        List<LinkedHashMap<String, Object>> typeList = rentInfoMapper.selectRentType();
        result.put("typeList", typeList);
        return result;
    }
}
