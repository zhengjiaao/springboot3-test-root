package com.zja.security.jwt.model;


/**
 * @Author: zhengja
 * @Date: 2025-08-04 17:32
 */
public record RegisterRequest(
        String firstname,
        String lastname,
        String email,
        String password
) {
}
