package com.hzy.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzy.auth.mapper.SysMenuMapper;
import com.hzy.auth.service.SysMenuService;
import com.hzy.auth.service.SysRoleMenuService;
import com.hzy.auth.utils.MenuHelper;
import com.hzy.common.config.exception.zyException;
import com.hzy.common.result.ResultCodeEnum;
import com.hzy.model.system.SysMenu;
import com.hzy.model.system.SysRoleMenu;
import com.hzy.vo.system.AssginMenuVo;
import com.hzy.vo.system.MetaVo;
import com.hzy.vo.system.RouterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
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
        wrapper.eq(SysMenu::getParentId, id);
        int count = count(wrapper);
        if (count > 0) {
            throw new zyException(ResultCodeEnum.FAIL.getCode(), "菜单不能删除");
        }
        removeById(id);
    }

    @Override
    public List<RouterVo> findUserMenuListByUserId(Long userId) {
        List<SysMenu> sysMenuList = null;
        // 判断当前用户是否是管理员   userId=1是管理员
        // 如果是管理员，查询所有菜单列表
        if (userId == 1) {
            // 查询所有菜单列表
            LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysMenu::getStatus, 1);
            wrapper.orderByAsc(SysMenu::getSortValue);
            sysMenuList = this.list(wrapper);
        } else {
            // 如果不是管理员，根据userId查询可以操作菜单列表
            // 多表关联查询：用户角色关系表 、 角色菜单关系表、 菜单表
            sysMenuList = baseMapper.findMenuListByUserId(userId);
        }
        // 把查询出来数据列表-构建成框架要求的路由结构
        // 使用菜单操作工具类构建树形结构
        List<SysMenu> sysMenuTreeList = MenuHelper.buildTree(sysMenuList);

        // 构建成框架要求的路由结构
        List<RouterVo> routerList = this.buildRouter(sysMenuTreeList);

        return routerList;
    }

    /**
     * 根据菜单构建路由
     * @param menus
     * @return
     */
    private List<RouterVo> buildRouter(List<SysMenu> menus) {
        List<RouterVo> routers = new LinkedList<RouterVo>();
        for (SysMenu menu : menus) {
            RouterVo router = new RouterVo();
            router.setHidden(false);
            router.setAlwaysShow(false);
            router.setPath(getRouterPath(menu));
            router.setComponent(menu.getComponent());
            router.setMeta(new MetaVo(menu.getName(), menu.getIcon()));
            List<SysMenu> children = menu.getChildren();
            if (menu.getType() == 1){
                // 加载隐藏路由
                List<SysMenu> hiddenMenuList = children.stream()
                        .filter(item -> !StringUtils.isEmpty(item.getComponent()))
                        .collect(Collectors.toList());
                for (SysMenu hiddenMenu : hiddenMenuList){
                    RouterVo hiddenRouter = new RouterVo();
                    hiddenRouter.setHidden(true);
                    hiddenRouter.setAlwaysShow(false);
                    hiddenRouter.setPath(getRouterPath(hiddenMenu));
                    hiddenRouter.setComponent(hiddenMenu.getComponent());
                    hiddenRouter.setMeta(new MetaVo(hiddenMenu.getName(), hiddenMenu.getIcon()));
                    routers.add(hiddenRouter);
                }
            }else {
                if (!CollectionUtils.isEmpty(children)) {
                    if(children.size() > 0) {
                        router.setAlwaysShow(true);
                    }
                    router.setChildren(buildRouter(children));
                }
            }
            routers.add(router);
        }


        return routers;
    }
    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(SysMenu menu) {
        String routerPath = "/" + menu.getPath();
        if(menu.getParentId().intValue() != 0) {
            routerPath = menu.getPath();
        }
        return routerPath;
    }
    @Override
    public List<String> findUserPermsByUserId(Long userId) {
        //超级管理员admin账号id为：1
        List<SysMenu> sysMenuList = null;
        if (userId.longValue() == 1) {
            sysMenuList = this.list(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getStatus, 1));
        } else {
            // 如果不是管理员，根据userId查询可以操作菜单列表
            // 多表关联查询：用户角色关系表 、 角色菜单关系表、 菜单表
            sysMenuList = baseMapper.findMenuListByUserId(userId);
        }
        List<String> permsList = sysMenuList.stream()
                .filter(item -> item.getType() == 2)
                .map(item -> item.getPerms())
                .collect(Collectors.toList());
        return permsList;
    }
}
