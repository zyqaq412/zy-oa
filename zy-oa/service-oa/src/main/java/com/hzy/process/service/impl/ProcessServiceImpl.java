package com.hzy.process.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzy.process.mapper.ProcessMapper;
import com.hzy.process.service.ProcessService;
import com.hzy.vo.process.ProcessQueryVo;
import com.hzy.vo.process.ProcessVo;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.util.zip.ZipInputStream;

/**
 * 审批类型(OaProcess)表服务实现类
 *
 * @author makejava
 * @since 2023-07-10 15:41:31
 */

public class ProcessServiceImpl extends ServiceImpl<ProcessMapper, Process> implements ProcessService {

    @Autowired
    private ProcessMapper processMapper;
    // 部署流程定义
    @Autowired
    private RepositoryService repositoryService;
    @Override
    public IPage<ProcessVo> selectPage(Page<ProcessVo> pageParam, ProcessQueryVo processQueryVo) {
        IPage<ProcessVo> page = processMapper.selectPage(pageParam, processQueryVo);
        return page;
    }

    @Override
    public void deployByZip(String deployPath) {
        // 定义zip输入流
        InputStream inputStream = this
                .getClass()
                .getClassLoader()
                .getResourceAsStream(deployPath);
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        // 流程部署
        Deployment deployment = repositoryService.createDeployment()
                .addZipInputStream(zipInputStream)
                .deploy();
        System.out.println(deployment.getId());
        System.out.println(deployment.getName());
    }
}
