package com.hzy.auth;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hzy.auth.mapper.SysRoleMapper;
import com.hzy.model.sytem.SysRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @title: SysRoleMapperTest
 * @Author zxwyhzy
 * @Date: 2023/6/3 21:57
 * @Version 1.0
 */
@SpringBootTest
public class SysRoleMapperTest {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    // 查询所有记录
    @Test
    public void getAll(){
        List<SysRole> sysRoles = sysRoleMapper.selectList(null);
        sysRoles.forEach(System.out::println);
    }

    // 条件构造器查询
    @Test
    public void getByRoleCode(){
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(SysRole::getRoleCode,"SYSTEM");
        List<SysRole> sysRoles = sysRoleMapper.selectList(queryWrapper);
        sysRoles.forEach(System.out::println);
    }
    // 测试逻辑删除
    @Test
    public void deleted(){
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(SysRole::getRoleCode,"SYSTEM");
        // 断言
        assert  sysRoleMapper.delete(queryWrapper) == 1;
    }
}
