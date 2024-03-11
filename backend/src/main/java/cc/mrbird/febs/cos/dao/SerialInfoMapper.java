package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.SerialInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface SerialInfoMapper extends BaseMapper<SerialInfo> {

    /**
     * 分页获取序列信息
     *
     * @param page 分页对象
     * @param serialInfo 序列信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectSerialInfoPage(Page<SerialInfo> page, @Param("serialInfo") SerialInfo serialInfo);
}
