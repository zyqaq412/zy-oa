package com.hzy.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzy.common.result.Result;
import com.hzy.model.sytem.SysUser;
import com.hzy.vo.system.SysUserQueryVo;


/**
 * 用户表(SysUser)表服务接口
 *
 * @author makejava
 * @since 2023-06-14 13:20:35
 */
public interface SysUserService extends IService<SysUser> {

    /**
     *  条件分页查询
     * @param page 页码
     * @param limit 每页条数
     * @param sysUserQueryVo 条件
     * @return 查询结果
     */
    Result pageQueryRole(Long page, Long limit, SysUserQueryVo sysUserQueryVo);

    /**
     *  更改用户状态
     * @param id 用户id
     * @param status 状态值
     */
    void updateStatus(Long id, Integer status);
}

