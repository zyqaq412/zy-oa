package com.hzy.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzy.auth.mapper.SysUserRoleMapper;
import com.hzy.auth.service.SysUserRoleService;
import com.hzy.model.sytem.SysUserRole;
import org.springframework.stereotype.Service;

/**
 * 用户角色(SysUserRole)表服务实现类
 *
 * @author makejava
 * @since 2023-06-14 13:21:12
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

}
