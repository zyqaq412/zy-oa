package com.hzy.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzy.auth.mapper.ProcessTemplateMapper;
import com.hzy.auth.service.ProcessTemplateService;
import com.hzy.model.process.ProcessTemplate;
import org.springframework.stereotype.Service;

/**
 * 审批模板(OaProcessTemplate)表服务实现类
 *
 * @author makejava
 * @since 2023-07-07 21:26:52
 */
@Service("oaProcessTemplateService")
public class ProcessTemplateServiceImpl extends ServiceImpl<ProcessTemplateMapper, ProcessTemplate> implements ProcessTemplateService {

}
