package com.zja.data.jpa.service;

import com.zja.data.jpa.model.dto.PageData;
import com.zja.data.jpa.model.dto.ProjectDTO;
import com.zja.data.jpa.model.request.ProjectPageRequest;
import com.zja.data.jpa.model.request.ProjectRequest;
import com.zja.data.jpa.model.request.ProjectUpdateRequest;

import java.util.List;

/**
 * 项目 服务层
 *
 * @author: zhengja
 * @since: 2025/11/12 14:23
 */
public interface ProjectService {

    /**
     * 查询项目
     *
     * @param id 项目id
     */
    ProjectDTO queryById(String id);

    /**
     * 分页查询项目
     */
    PageData<ProjectDTO> pageList(ProjectPageRequest request);

    /**
     * 校验项目名称是否可用
     *
     * @param name 项目名称
     * @return Boolean
     */
    Boolean existName(String name);

    /**
     * 新增项目
     */
    ProjectDTO add(ProjectRequest request);

    /**
     * 批量添加项目
     */
    List<ProjectDTO> addBatch(List<ProjectRequest> requests);

    /**
     * 更新项目
     *
     * @param id 项目id
     */
    ProjectDTO update(String id, ProjectUpdateRequest request);

    /**
     * 删除项目
     *
     * @param id 项目id
     */
    boolean deleteById(String id);

    /**
     * 批量删除项目
     *
     * @param ids 项目ids
     */
    void deleteBatch(List<String> ids);

}