package com.zja.data.jpa.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 项目 更新参数
 *
 * @author: zhengja
 * @since: 2025/11/12 14:27
 */
@Data
@Schema(description = "ProjectUpdateRequest 更新 项目信息")
public class ProjectUpdateRequest implements Serializable {
    @Schema(description = "项目名称")
    private String name;

    @Schema(description = "项目描述")
    private String remarks;

}