package com.zja.data.jpa.service;

import com.zja.data.jpa.dao.ProjectRepo;
import com.zja.data.jpa.mapper.ProjectMapper;
import com.zja.data.jpa.model.dto.PageData;
import com.zja.data.jpa.model.dto.ProjectDTO;
import com.zja.data.jpa.model.entity.Project;
import com.zja.data.jpa.model.request.ProjectPageRequest;
import com.zja.data.jpa.model.request.ProjectRequest;
import com.zja.data.jpa.model.request.ProjectUpdateRequest;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Project 业务处理服务实现层
 *
 * @author: zhengja
 * @since: 2025/11/12 14:23
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepo repo;
    private final ProjectMapper mapper;

    private Project getProjectById(String id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException(String.format("传入的 id=%s 有误！", id)));
    }

    @Override
    public ProjectDTO queryById(String id) {
        Project entity = this.getProjectById(id);
        return mapper.map(entity);
    }

    @Override
    public PageData<ProjectDTO> pageList(ProjectPageRequest request) {
        int page = request.getPage();
        int size = request.getSize();
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");

        // 查询条件
        Specification<Project> spec = buildQuery(request);
        // 分页查询
        Page<Project> sourcePage = repo.findAll(spec, PageRequest.of(page, size, sort));

        return PageData.of(mapper.mapList(sourcePage.getContent()), page, size, sourcePage.getTotalElements());
    }

    private Specification<Project> buildQuery(ProjectPageRequest request) {
        // 构建查询条件
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 关键词
            if (StringUtils.hasText(request.getName())) {
                predicates.add(cb.like(root.get("name"), request.getName() + "%"));
                // predicates.add(cb.equal(root.get("name"), request.getName()));
            }
            // 将条件连接在一起
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    @Override
    public Boolean existName(String name) {
        Optional<Project> byName = repo.findByName(name);
        return byName.isPresent();
    }

    @Override
    public ProjectDTO add(ProjectRequest request) {
        // 检验名字已存在
        if (existName(request.getName())) {
            throw new UnsupportedOperationException("名称不能重复");
        }

        Project entity = mapper.map(request);
        entity = repo.save(entity);

        return mapper.map(entity);
    }

    @Override
    public List<ProjectDTO> addBatch(List<ProjectRequest> requests) {
        return requests.stream().map(this::add).collect(Collectors.toList());
    }

    @Override
    public ProjectDTO update(String id, ProjectUpdateRequest request) {
        // 校验存在
        Project entity = this.getProjectById(id);

        // 更新
        BeanUtils.copyProperties(request, entity);
        entity = repo.save(entity);

        return mapper.map(entity);
    }

    @Override
    public boolean deleteById(String id) {
        try {
            if (repo.findById(id).isPresent()) {
                repo.deleteById(id);
            }
            return true;
        } catch (Exception e) {
            log.error("Failed to delete entity with ID: {}", id, e);
            return false;
        }
    }

    public void deleteBatch(List<String> ids) {
        ids.forEach(this::deleteById);
    }

}