package com.hzy.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzy.model.sytem.SysMenu;
import com.hzy.vo.system.AssginMenuVo;

import java.util.List;


/**
 * 菜单表(SysMenu)表服务接口
 *
 * @author makejava
 * @since 2023-06-14 20:24:54
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 根据角色id查询菜单
     * @param roleId 角色id
     * @return 菜单树
     */
    List<SysMenu> findMenuByRoleId(Long roleId);

    /**
     * 角色分配菜单
     * @param assginMenuVo 角色菜单关联
     */
    void doAssign(AssginMenuVo assginMenuVo);

    /**
     *
     * @return 查询菜单列表
     */
    List<SysMenu> findNodes();

    /**
     * 根据菜单id删除菜单
     * @param id 菜单id
     */
    void removeMenuById(Long id);
}

