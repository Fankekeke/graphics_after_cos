package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.SerialInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface ISerialInfoService extends IService<SerialInfo> {

    /**
     * 分页获取序列信息
     *
     * @param page 分页对象
     * @param serialInfo 序列信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectSerialInfoPage(Page<SerialInfo> page, SerialInfo serialInfo);
}
