package com.hzy.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzy.auth.mapper.SysRoleMenuMapper;
import com.hzy.auth.service.SysRoleMenuService;
import com.hzy.model.system.SysRoleMenu;
import org.springframework.stereotype.Service;

/**
 * 角色菜单(SysRoleMenu)表服务实现类
 *
 * @author makejava
 * @since 2023-06-14 21:22:20
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

}
