package com.zja.data.jpa.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Project 数据传输
 *
 * @author: zhengja
 * @since: 2025/11/12 14:26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "ProjectDTO")
public class ProjectDTO implements Serializable {
    @Schema(description = "id")
    private String id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "备注")
    private String remarks;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime lastModifiedDate;
}