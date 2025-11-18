package com.zja.data.jpa.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 项目 请求参数
 *
 * @author: zhengja
 * @since: 2025/11/12 14:24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "ProjectRequest 新增 或 更新 项目信息")
public class ProjectRequest implements Serializable {
    @Schema(description = "项目名称")
    private String name;

    @Schema(description = "项目备注")
    private String remarks;

}