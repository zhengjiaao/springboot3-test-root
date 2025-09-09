package com.zja.api.openapi.security.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Author: zhengja
 * @Date: 2025-08-04 13:25
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 这里应该从数据库或其他存储中获取用户信息
        // 为了演示，使用硬编码的用户
        if ("admin".equals(username)) {
            return User.builder()
                    .username("admin")
                    .password("{noop}admin123") // {noop} 表示不加密
                    .roles("USER", "ADMIN")
                    .build();
        } else if ("user".equals(username)) {
            return User.builder()
                    .username("user")
                    .password("{noop}password")
                    .roles("USER")
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found: " + username);
        }
    }
}