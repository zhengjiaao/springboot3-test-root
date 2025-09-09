package com.zja.security.jwt.model;

/**
 * Authentication 数据传输
 *
 * @author: zhengja
 * @since: 2025/08/04 17:30
 */
public record AuthenticationDTO(
        String id,
        String name
) { }