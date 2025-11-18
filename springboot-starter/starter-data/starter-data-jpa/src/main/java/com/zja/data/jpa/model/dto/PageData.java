package com.zja.data.jpa.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nonnull;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * a 数据传输
 *
 * @author: zhengja
 * @since: 2025/11/12 14:34
 */
@Data
@Schema(description = "PageData 分页响应")
public final class PageData<T> implements Serializable {

    @Schema(description = "数据")
    private List<T> data;
    @Schema(description = "当前页号")
    private int index;
    @Schema(description = "页大小")
    private int size;
    @Schema(description = "查询到的数据量")
    private int length;
    @Schema(description = "总数据量")
    private long count;
    @Schema(description = "总页数")
    private int pages;
    @Schema(description = "是否第一页")
    private boolean first;
    @Schema(description = "是否最后一页")
    private boolean last;
    @Schema(description = "是否有下一页")
    private boolean hasNext;
    @Schema(description = "是否有上一页")
    private boolean hasPrev;

    public static <T> PageData<T> of(Page<T> page) {
        return of(page.getContent(), page.getNumber(), page.getSize(), page.getTotalElements());
    }

    public static <T> PageData<T> of(@Nonnull List<T> data, int pageIndex, int pageSize, long totalSize) {
        PageData<T> dp = new PageData<>();
        dp.data = data;
        dp.index = pageIndex;
        dp.size = pageSize;
        dp.length = data.size();
        dp.count = totalSize;
        dp.pages = BigDecimal.valueOf(totalSize).divide(BigDecimal.valueOf(pageSize), RoundingMode.UP).intValue();
        dp.first = pageIndex == 0;
        dp.hasPrev = !dp.first;
        dp.hasNext = (dp.pages - dp.index) > 1;
        dp.last = !dp.hasNext;
        return dp;
    }

    public void convert(Function<T, T> mapper) {
        this.data = data.stream().map(mapper).collect(Collectors.toList());
    }

    public <R extends Serializable> PageData<R> map(Function<List<T>, List<R>> mapper) {
        return of(mapper.apply(this.getData()), this.getIndex(), this.getSize(), this.getCount());
    }
}