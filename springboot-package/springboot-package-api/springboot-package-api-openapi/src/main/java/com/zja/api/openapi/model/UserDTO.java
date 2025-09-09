package com.zja.api.openapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: zhengja
 * @since: 2019/6/27 16:35
 */
@Schema(description = "用户信息")
@Data
public class UserDTO implements Serializable {
    @Schema(description = "用户id")
    private String id;
    @Schema(description = "用户名")
    private String name;
    @Schema(description = "时间")
    private Date date;
}
