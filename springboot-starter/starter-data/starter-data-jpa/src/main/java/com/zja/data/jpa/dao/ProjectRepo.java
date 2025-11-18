package com.zja.data.jpa.dao;

import com.zja.data.jpa.model.entity.Project;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Project SQL
 *
 * @author: zhengja
 * @since: 2025/11/12 14:22
 */
@Repository
public interface ProjectRepo extends JpaRepository<Project, String>, CrudRepository<Project, String>,
        JpaSpecificationExecutor<Project> {

    Optional<Project> findByName(String name);
}