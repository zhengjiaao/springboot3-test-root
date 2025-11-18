package com.zja.data.jpa.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 项目 分页参数
 *
 * @author: zhengja
 * @since: 2025/11/12 14:32
 */
@Getter
@Setter
@Schema(description = "Project 分页参数")
public class ProjectPageRequest extends BasePageRequest {
    @Schema(description = "项目名称")
    private String name;

}