package com.zja.security.jwt.model;

/**
 * @Author: zhengja
 * @Date: 2025-08-04 17:35
 */
public record AuthenticationRequest(
        String email,
        String password
) {
}
