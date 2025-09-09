package com.zja.security.jwt.service;

import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @Author: zhengja
 * @Date: 2025-08-04 17:16
 */
public interface UserService {
    UserDetailsService userDetailsService();
}