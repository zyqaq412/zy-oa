package com.hzy.vo.system;

import java.io.Serializable;

/**
 * @title: SysRoleQueryVo
 * @Author zxwyhzy
 * @Date: 2023/6/3 23:58
 * @Version 1.0
 */
public class SysRoleQueryVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}