package com.zja.security.jwt.dao;

import com.zja.security.jwt.model.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * User SQL
 *
 * @author: zhengja
 * @since: 2025/08/04 17:25
 */
@Repository
public interface UserRepository extends JpaRepository<User, String>, CrudRepository<User, String>,
        JpaSpecificationExecutor<User> {

    Optional<User> findByEmail(String email);
}