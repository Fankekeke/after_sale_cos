package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.ReserveInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IReserveInfoService extends IService<ReserveInfo> {

    /**
     * 分页获取预约信息
     *
     * @param page 分页对象
     * @param reserveInfo 预约信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectReservePage(Page<ReserveInfo> page, ReserveInfo reserveInfo);
}
