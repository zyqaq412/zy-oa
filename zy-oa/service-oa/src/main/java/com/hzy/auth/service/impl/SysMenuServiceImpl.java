package com.hzy.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzy.auth.mapper.SysMenuMapper;
import com.hzy.auth.service.SysMenuService;
import com.hzy.auth.service.SysRoleMenuService;
import com.hzy.auth.utils.MenuHelper;
import com.hzy.common.config.exception.zyException;
import com.hzy.common.result.ResultCodeEnum;
import com.hzy.model.sytem.SysMenu;
import com.hzy.model.sytem.SysRoleMenu;
import com.hzy.vo.system.AssginMenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单表(SysMenu)表服务实现类
 *
 * @author makejava
 * @since 2023-06-14 20:24:54
 */
@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Override
    public List<SysMenu> findMenuByRoleId(Long roleId) {
        //全部权限列表
        List<SysMenu> allSysMenuList =
                this.list(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getStatus, 1));

        //根据角色id获取角色权限
        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuService
                .list(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, roleId));
        //转换给角色id与角色权限对应Map对象
        List<Long> menuIdList = sysRoleMenuList.stream()
                .map(e -> e.getMenuId())
                .collect(Collectors.toList());

        allSysMenuList.forEach(permission -> {
            if (menuIdList.contains(permission.getId())) {
                permission.setSelect(true);
            } else {
                permission.setSelect(false);
            }
        });

        List<SysMenu> sysMenuList = MenuHelper.buildTree(allSysMenuList);
        return sysMenuList;
    }

    @Transactional
    @Override
    public void doAssign(AssginMenuVo assginMenuVo) {
        // 先删除原来的菜单关联
        sysRoleMenuService.remove(
                new LambdaQueryWrapper<SysRoleMenu>()
                        .eq(SysRoleMenu::getRoleId, assginMenuVo.getRoleId()));

        // 重新绑定菜单关联
        // 取出每一个菜单id
        for (Long menuId : assginMenuVo.getMenuIdList()) {
            if (StringUtils.isEmpty(menuId)) continue;

            SysRoleMenu rolePermission = new SysRoleMenu();
            rolePermission.setRoleId(assginMenuVo.getRoleId());
            rolePermission.setMenuId(menuId);

            sysRoleMenuService.save(rolePermission);
        }
    }

    @Override
    public List<SysMenu> findNodes() {
        // 获取全部菜单
        List<SysMenu> sysMenuList = list();
        // 构建树形结构
        List<SysMenu> treeMenuList = MenuHelper.buildTree(sysMenuList);

        return treeMenuList;
    }

    @Override
    public void removeMenuById(Long id) {
        // 有子菜单不能删除
        // 获取全部菜单
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysMenu::getParentId,id);
        int count = count(wrapper);
        if (count > 0){
            throw new zyException(ResultCodeEnum.FAIL.getCode(),"菜单不能删除");
        }
        removeById(id);
    }
}
