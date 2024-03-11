package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.SerialInfo;
import cc.mrbird.febs.cos.service.ISerialInfoService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author FanK
 */
@RestController
@RequestMapping("/cos/serial-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SerialInfoController {

    private final ISerialInfoService serialInfoService;

    /**
     * 分页获取序列信息
     *
     * @param page 分页对象
     * @param serialInfo 序列信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<SerialInfo> page, SerialInfo serialInfo) {
        return R.ok(serialInfoService.selectSerialInfoPage(page, serialInfo));
    }

    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(serialInfoService.getById(id));
    }

    @GetMapping("/list")
    public R list() {
        return R.ok(serialInfoService.list());
    }

    /**
     * 新增序列信息
     *
     * @param serialInfo 序列信息
     * @return 结果
     */
    @PostMapping
    public R save(SerialInfo serialInfo) throws FebsException {
        // 校验序列号是否重复
        if (StrUtil.isEmpty(serialInfo.getSerialNumber())) {
            throw new FebsException("序列号不能为空！");
        }
        int count = serialInfoService.count(Wrappers.<SerialInfo>lambdaQuery().eq(SerialInfo::getSerialNumber, serialInfo.getSerialNumber()));
        if (count > 0) {
            throw new FebsException("序列号不能重复！");
        }
        serialInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(serialInfoService.save(serialInfo));
    }

    /**
     * 修改序列信息
     *
     * @param serialInfo 序列信息
     * @return 结果
     */
    @PutMapping
    public R edit(SerialInfo serialInfo) throws FebsException {
        // 校验序列号是否重复
        if (StrUtil.isEmpty(serialInfo.getSerialNumber())) {
            throw new FebsException("序列号不能为空！");
        }
        List<SerialInfo> serialInfoList = serialInfoService.list(Wrappers.<SerialInfo>lambdaQuery().eq(SerialInfo::getSerialNumber, serialInfo.getSerialNumber()));
        if (CollectionUtil.isNotEmpty(serialInfoList) && !(serialInfoList.size() == 1 && serialInfoList.get(0).getId().equals(serialInfo.getId()))) {
            throw new FebsException("序列号不能重复！");
        }
        return R.ok(serialInfoService.updateById(serialInfo));
    }

    /**
     * 删除序列信息
     *
     * @param ids ids
     * @return 序列信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(serialInfoService.removeByIds(ids));
    }
}
