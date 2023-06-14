package com.hzy.auth.controller;

import com.hzy.auth.service.SysUserService;
import com.hzy.common.result.Result;
import com.hzy.vo.system.SysUserQueryVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @title: SysUserController
 * @Author zxwyhzy
 * @Date: 2023/6/14 13:25
 * @Version 1.0
 */
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @ApiOperation("用户条件分页查询")
    @GetMapping("{page}/{limit}")
    public Result index(@PathVariable Long page, @PathVariable Long limit, SysUserQueryVo sysUserQueryVo){



        return Result.ok();
    }
}
