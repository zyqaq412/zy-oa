package com.hzy.vo.system;

/**
 * @title: SysUserQueryVo 用户查询实体
 * @Author zxwyhzy
 * @Date: 2023/6/14 13:30
 * @Version 1.0
 */

import lombok.Data;

import java.io.Serializable;
@Data
public class SysUserQueryVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String keyword;

    private String createTimeBegin;
    private String createTimeEnd;

    private Long roleId;
    private Long postId;
    private Long deptId;

}
