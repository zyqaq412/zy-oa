package com.hzy.process.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzy.vo.process.ProcessQueryVo;
import com.hzy.vo.process.ProcessVo;
import org.springframework.data.repository.query.Param;


/**
 * 审批类型(OaProcess)表数据库访问层
 *
 * @author makejava
 * @since 2023-07-10 15:41:31
 */
public interface ProcessMapper extends BaseMapper<Process> {
    IPage<ProcessVo> selectPage(Page<ProcessVo> page, @Param("vo") ProcessQueryVo processQueryVo);
}
