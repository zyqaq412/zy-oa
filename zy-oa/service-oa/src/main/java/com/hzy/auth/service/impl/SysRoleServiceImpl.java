package com.hzy.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzy.auth.mapper.SysRoleMapper;
import com.hzy.auth.service.SysRoleService;
import com.hzy.common.result.Result;
import com.hzy.model.sytem.SysRole;
import com.hzy.vo.system.SysRoleQueryVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @title: SysRoleServiceImpl
 * @Author zxwyhzy
 * @Date: 2023/6/3 22:29
 * @Version 1.0
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService  {

    @Override
    public Result pageQueryRole(Long page, Long limit, SysRoleQueryVo sysRoleQueryVo) {
        // 调用service的方法实现
        // 1 创建Page对象，传递分页相关参数
        // page 当前页  limit 每页显示记录数
        Page<SysRole> pageParam = new Page<>(page, limit);

        // 2 封装条件，判断条件是否为空，不为空进行封装
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        String roleName = sysRoleQueryVo.getRoleName();
        if (!StringUtils.isEmpty(roleName)) {
            // 封装 like模糊查询
            wrapper.like(SysRole::getRoleName, roleName);
        }

        // 3 调用方法实现
        IPage<SysRole> pageModel = page(pageParam, wrapper);

        return Result.ok(pageModel);
    }
}
