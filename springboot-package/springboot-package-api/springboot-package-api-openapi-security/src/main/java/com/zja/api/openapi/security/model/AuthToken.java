package com.zja.api.openapi.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: zhengja
 * @Date: 2025-08-04 13:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthToken {
    private String token;
    private String type = "Bearer";

    public AuthToken(String token) {
        this.token = token;
    }
}
