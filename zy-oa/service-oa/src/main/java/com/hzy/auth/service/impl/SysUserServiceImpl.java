package com.hzy.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzy.auth.mapper.SysUserMapper;
import com.hzy.auth.service.SysUserService;
import com.hzy.common.result.Result;
import com.hzy.model.system.SysUser;
import com.hzy.vo.system.SysUserQueryVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 用户表(SysUser)表服务实现类
 *
 * @author makejava
 * @since 2023-06-14 13:20:35
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public Result pageQueryRole(Long page, Long limit, SysUserQueryVo sysUserQueryVo) {
        Page<SysUser> sysUserPage = new Page<>(page, limit);
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();

        String username = sysUserQueryVo.getKeyword();
        String createTimeBegin = sysUserQueryVo.getCreateTimeBegin();
        String createTimeEnd = sysUserQueryVo.getCreateTimeEnd();

        // 模糊查询
        wrapper.like(!StringUtils.isEmpty(username),SysUser::getName,username);
        // 大于等于
        wrapper.ge(!StringUtils.isEmpty(createTimeBegin),SysUser::getCreateTime,createTimeBegin);
        // 小于等于
        wrapper.le(!StringUtils.isEmpty(createTimeEnd),SysUser::getCreateTime,createTimeEnd);

        Page<SysUser> pageModel = page(sysUserPage, wrapper);

        return Result.ok(pageModel);
    }

    @Transactional
    @Override
    public void updateStatus(Long id, Integer status) {
        SysUser sysUser = this.getById(id);
        if(status.intValue() == 1) {
            sysUser.setStatus(status);
        } else {
            sysUser.setStatus(0);
        }
        this.updateById(sysUser);
    }

    @Override
    public SysUser getUserByUsername(String username) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername,username);
        return getOne(wrapper);
    }
}
