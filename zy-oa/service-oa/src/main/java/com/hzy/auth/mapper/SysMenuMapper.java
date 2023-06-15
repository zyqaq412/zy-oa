package com.hzy.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzy.model.system.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 菜单表(SysMenu)表数据库访问层
 *
 * @author makejava
 * @since 2023-06-14 20:24:54
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> findMenuListByUserId(@Param("userId") Long userId);
}
