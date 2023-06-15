package com.hzy.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzy.common.result.Result;
import com.hzy.model.system.SysRole;
import com.hzy.vo.system.AssginRoleVo;
import com.hzy.vo.system.SysRoleQueryVo;

import java.util.Map;

/**
 * @title: SysRoleService
 * @Author zxwyhzy
 * @Date: 2023/6/3 22:29
 * @Version 1.0
 */
public interface SysRoleService extends IService<SysRole> {
    /**
     *  条件分页查询
     * @param page 页码
     * @param limit 每页条数
     * @param sysRoleQueryVo 条件
     * @return 查询结果
     */
    Result pageQueryRole(Long page, Long limit, SysRoleQueryVo sysRoleQueryVo);

    /**
     *  根据用户获取角色
     * @param userId 用户id
     * @return 用户拥有角色
     */
    Map<String,Object> findRoleByUserId(Long userId);

    /**
     *  为用户分配角色
     * @param assginRoleVo 用户角色关联类
     */
    void doAssign(AssginRoleVo assginRoleVo);
}
