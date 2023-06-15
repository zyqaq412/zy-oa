package com.hzy.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzy.auth.mapper.SysRoleMapper;
import com.hzy.auth.service.SysRoleService;
import com.hzy.auth.service.SysUserRoleService;
import com.hzy.common.result.Result;
import com.hzy.model.system.SysRole;
import com.hzy.model.system.SysUserRole;
import com.hzy.vo.system.AssginRoleVo;
import com.hzy.vo.system.SysRoleQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @title: SysRoleServiceImpl
 * @Author zxwyhzy
 * @Date: 2023/6/3 22:29
 * @Version 1.0
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService  {

    @Autowired
    private SysUserRoleService sysUserRoleService;
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

    @Override
    public Map<String, Object> findRoleByUserId(Long userId) {
        //查询所有的角色
        List<SysRole> allRolesList = this.list();

        //拥有的角色id
        List<SysUserRole> existUserRoleList = sysUserRoleService
                .list(new LambdaQueryWrapper<SysUserRole>()
                        .eq(SysUserRole::getUserId, userId));

        List<Long> existRoleIdList = existUserRoleList.stream()
                .map(SysUserRole::getRoleId)
                .collect(Collectors.toList());

        //对角色进行分类
        List<SysRole> assginRoleList = new ArrayList<>();
        for (SysRole role : allRolesList) {
            //已分配
            if(existRoleIdList.contains(role.getId())) {
                assginRoleList.add(role);
            }
        }

        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("assginRoleList", assginRoleList);
        roleMap.put("allRolesList", allRolesList);
        return roleMap;
    }

    @Transactional
    @Override
    public void doAssign(AssginRoleVo assginRoleVo) {
        // 删除之前的角色分配
        LambdaQueryWrapper<SysUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserRole::getUserId,assginRoleVo.getUserId());
        sysUserRoleService.remove(wrapper);

        // 重新进行分配
        List<Long> roleIdList = assginRoleVo.getRoleIdList();
        roleIdList.forEach(id -> {
                    SysUserRole sysUserRole = new SysUserRole();
                    sysUserRole.setRoleId(id);
                    sysUserRole.setUserId(assginRoleVo.getUserId());
                    sysUserRoleService.save(sysUserRole);
                });

    }
}
