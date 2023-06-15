package com.hzy.vo.system;

import lombok.Data;

/**
 * @title: MetaVo 路由显示信息
 * @Author zxwyhzy
 * @Date: 2023/6/15 19:23
 * @Version 1.0
 */
@Data
public class MetaVo
{
    /**
     * 设置该路由在侧边栏和面包屑中展示的名字
     */
    private String title;

    /**
     * 设置该路由的图标，对应路径src/assets/icons/svg
     */
    private String icon;

    public MetaVo()
    {
    }

    public MetaVo(String title, String icon)
    {
        this.title = title;
        this.icon = icon;
    }

}
