package com.hzy.auth.controller;

/**
 * @title: LoginController 后台登录登出
 * @Author zxwyhzy
 * @Date: 2023/6/4 15:42
 * @Version 1.0
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hzy.auth.service.SysMenuService;
import com.hzy.auth.service.SysUserService;
import com.hzy.common.config.exception.zyException;
import com.hzy.common.jwt.JwtHelper;
import com.hzy.common.result.Result;
import com.hzy.common.result.ResultCodeEnum;
import com.hzy.model.system.SysUser;
import com.hzy.vo.system.LoginVo;
import com.hzy.vo.system.RouterVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "后台登录管理")
@RestController
@RequestMapping("/admin/system/index")
public class LoginController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 登录
     *
     * @return
     */
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, loginVo.getUsername());
        SysUser user = sysUserService.getOne(wrapper);
        if (null == user) {
            throw new zyException(ResultCodeEnum.FAIL.getCode(), "用户不存在");
        }
        String password = DigestUtils.md5DigestAsHex(loginVo.getPassword().getBytes());
        if (!user.getPassword().equals(password)) {
            throw new zyException(ResultCodeEnum.FAIL.getCode(), "密码错误");
        }
        if (user.getStatus().intValue() == 0) {
            throw new zyException(ResultCodeEnum.FAIL.getCode(), "用户被禁用");
        }
        String token = JwtHelper.createToken(user.getId(), user.getUsername());
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        return Result.ok(map);
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @GetMapping("info")
    public Result info(HttpServletRequest request) {
        String token = request.getHeader("header");

        Long userId = JwtHelper.getUserId(token);

        SysUser user = sysUserService.getById(userId);

        // 获取用户可操作的菜单
        List<RouterVo> routerList = sysMenuService.findUserMenuListByUserId(userId);
        // 获取用户可操作的按钮
        List<String> permsList = sysMenuService.findUserPermsByUserId(userId);

        Map<String, Object> map = new HashMap<>();
        map.put("roles", "[admin]");
        map.put("name", user.getName());
        map.put("avatar", "https://zyqaq-blog.oss-cn-chengdu.aliyuncs.com/2023/03/26/08014c99b6bc460fb0d482f354017321.png");
        map.put("routers", routerList);
        map.put("buttons", permsList);
        return Result.ok(map);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("logout")
    public Result logout() {
        return Result.ok();
    }

}