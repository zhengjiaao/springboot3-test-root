package com.zja.data.jpa.controller;

import com.zja.data.jpa.model.dto.PageData;
import com.zja.data.jpa.model.dto.ProjectDTO;
import com.zja.data.jpa.model.request.ProjectPageRequest;
import com.zja.data.jpa.model.request.ProjectRequest;
import com.zja.data.jpa.model.request.ProjectUpdateRequest;
import com.zja.data.jpa.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 项目 接口层（一般与页面、功能对应）
 *
 * @author: zhengja
 * @since: 2025/11/12 14:43
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/project")
@Tag(name = "ProjectController", description = "项目管理页面")
public class ProjectController {

    private final ProjectService service;

    @GetMapping("/query/{id}")
    @Operation(summary = "查询单个项目详情", description = "返回 ProjectDTO")
    public ProjectDTO queryById(@NotBlank @PathVariable("id") String id) {
        return service.queryById(id);
    }

    @GetMapping("/page/list")
    @Operation(summary = "分页查询项目列表", description = "返回 PageData<ProjectDTO>")
    public PageData<ProjectDTO> pageList(@Valid ProjectPageRequest pageRequest) {
        return service.pageList(pageRequest);
    }

    /*@PostMapping("/page/list")
    @Operation(summary = "分页查询项目列表", description = "返回 PageData<ProjectDTO>")
    public PageData<ProjectDTO> pageList(@Valid @RequestBody ProjectPageRequest pageRequest) {
        return service.pageList(pageRequest);
    }*/

    @PostMapping("/add")
    @Operation(summary = "添加项目")
    public ProjectDTO add(@Valid @RequestBody ProjectRequest request) {
        return service.add(request);
    }

    @PostMapping("/add/batch")
    @Operation(summary = "批量添加项目")
    public List<ProjectDTO> add(@Valid @RequestBody List<ProjectRequest> requests) {
        return service.addBatch(requests);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "更新项目")
    public ProjectDTO update(@NotBlank @PathVariable("id") String id,
                             @Valid @RequestBody ProjectUpdateRequest updateRequest) {
        return service.update(id, updateRequest);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除项目")
    public boolean deleteById(@NotBlank @PathVariable("id") String id) {
        return service.deleteById(id);
    }

    @DeleteMapping("/delete/batch")
    @Operation(summary = "批量删除项目")
    public void deleteBatch(@RequestBody List<String> ids) {
        service.deleteBatch(ids);
    }
}