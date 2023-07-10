package com.hzy.process.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzy.vo.process.ProcessQueryVo;
import com.hzy.vo.process.ProcessVo;


/**
 * 审批类型(OaProcess)表服务接口
 *
 * @author makejava
 * @since 2023-07-10 15:41:31
 */
public interface ProcessService extends IService<Process> {
    IPage<ProcessVo> selectPage(Page<ProcessVo> pageParam, ProcessQueryVo processQueryVo);

    void deployByZip(String deployPath);
}

