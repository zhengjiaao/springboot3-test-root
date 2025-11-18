package com.zja.data.jpa.mapper;

import com.zja.data.jpa.model.dto.ProjectDTO;
import com.zja.data.jpa.model.entity.Project;
import com.zja.data.jpa.model.request.ProjectRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

/**
 * Project 属性映射
 *
 * @author: zhengja
 * @since: 2025/11/12 14:24
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProjectMapper {

    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class); // 如果不用 Spring 注入，可用此静态实例

    Project map(ProjectRequest request);

    ProjectDTO map(Project entity);

    Project map(ProjectDTO dto);

    List<ProjectDTO> mapList(List<Project> entityList);

    // Set、List、Map
    List<ProjectDTO> mapList(Collection<Project> Projects);
}
