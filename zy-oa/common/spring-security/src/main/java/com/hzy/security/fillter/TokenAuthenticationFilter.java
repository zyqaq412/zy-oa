package com.hzy.security.fillter;


import com.alibaba.fastjson.JSON;
import com.hzy.common.jwt.JwtHelper;
import com.hzy.common.result.Result;
import com.hzy.common.result.ResultCodeEnum;
import com.hzy.common.util.ResponseUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @title: TokenAuthenticationFilter 认证解析token过滤器
 * @Author zxwyhzy
 * @Date: 2023/6/16 14:59
 * @Version 1.0
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private RedisTemplate redisTemplate;
    public TokenAuthenticationFilter(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        logger.info("uri:"+request.getRequestURI());
        //如果是登录接口，直接放行
        if("/admin/system/index/login".equals(request.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        if(null != authentication) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } else {
            ResponseUtil.out(response, Result.build(null, ResultCodeEnum.PERMISSION));
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        // token置于header里
        String token = request.getHeader("token");
        logger.info("token:"+token);
        if (!StringUtils.isEmpty(token)) {
            String username = JwtHelper.getUsername(token);
            logger.info("username:"+username);
            if (!StringUtils.isEmpty(username)) {
                String authString =(String)redisTemplate.opsForValue().get(username);
                if (!StringUtils.isEmpty(authString)){
                    List<Map> mapList = JSON.parseArray(authString,Map.class);
                    List<SimpleGrantedAuthority> authList = new ArrayList<>();
                    for(Map map : mapList){
                        authList.add(new SimpleGrantedAuthority((String)map.get("authority")));
                    }
                    return new UsernamePasswordAuthenticationToken(username, null, authList);
                }
            }
        }
        return null;
    }
}