package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.SerialInfo;
import cc.mrbird.febs.cos.dao.SerialInfoMapper;
import cc.mrbird.febs.cos.service.ISerialInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class SerialInfoServiceImpl extends ServiceImpl<SerialInfoMapper, SerialInfo> implements ISerialInfoService {

    /**
     * 分页获取序列信息
     *
     * @param page 分页对象
     * @param serialInfo 序列信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectSerialInfoPage(Page<SerialInfo> page, SerialInfo serialInfo) {
        return baseMapper.selectSerialInfoPage(page, serialInfo);
    }
}
