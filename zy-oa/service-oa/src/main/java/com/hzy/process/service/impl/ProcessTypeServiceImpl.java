package com.hzy.process.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzy.process.mapper.ProcessTypeMapper;
import com.hzy.process.service.ProcessTypeService;
import com.hzy.model.process.ProcessType;
import org.springframework.stereotype.Service;

/**
 * 审批类型(OaProcessType)表服务实现类
 *
 * @author makejava
 * @since 2023-07-07 21:27:11
 */
@Service
public class ProcessTypeServiceImpl extends ServiceImpl<ProcessTypeMapper, ProcessType> implements ProcessTypeService {

}
