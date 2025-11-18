package com.zja.data.jpa.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 分页请求（通用的）
 *
 * @author: zhengja
 * @since: 2025/11/12 14:33
 */
@Setter
@Schema(description = "BasePageRequest 基础分页参数")
public class BasePageRequest implements Serializable {

    @Schema(description = "页码 从第1页开始")
    @Min(1)
    @Max(100000000)
    private Integer page = 1;

    @Getter
    @Schema(description = "每页数量 默认 10")
    @Min(1)
    @Max(100000000)
    private Integer size = 10;

    public Integer getPage() {
        return page - 1; // 注：jpa page 从 0 开始
    }
}