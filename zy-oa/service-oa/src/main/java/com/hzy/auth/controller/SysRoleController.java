package com.hzy.auth.controller;

import com.hzy.auth.service.SysRoleService;
import com.hzy.common.result.Result;
import com.hzy.model.sytem.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @title: SysRoleController
 * @Author zxwyhzy
 * @Date: 2023/6/3 22:40
 * @Version 1.0
 */
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @GetMapping("/findAll")
    public Result findAll(){
        // 调用Service的方法
        List<SysRole> list = sysRoleService.list();
        return Result.ok(list);
    }
}
