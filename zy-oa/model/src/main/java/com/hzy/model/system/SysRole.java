package com.hzy.model.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hzy.model.base.BaseEntity;
import lombok.Data;

/**
 * @title: SysRole
 * @Author zxwyhzy
 * @Date: 2023/6/3 21:46
 * @Version 1.0
 */
@Data
@TableName("sys_role")
public class SysRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    //角色名称
    @TableField("role_name")
    private String roleName;

    //角色编码
    @TableField("role_code")
    private String roleCode;

    //描述
    @TableField("description")
    private String description;
}

