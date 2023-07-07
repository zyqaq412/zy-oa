package com.hzy.process.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzy.model.process.ProcessTemplate;


/**
 * 审批模板(OaProcessTemplate)表服务接口
 *
 * @author makejava
 * @since 2023-07-07 21:26:52
 */
public interface ProcessTemplateService extends IService<ProcessTemplate> {
    IPage<ProcessTemplate> selectPageProcessTempate(Page<ProcessTemplate> pageParam);

    void publish(Long id);
}

