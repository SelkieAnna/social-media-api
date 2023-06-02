package com.selkieanna.socialmediaapi.authorization.repository;

import com.selkieanna.socialmediaapi.authorization.model.Role;
import com.selkieanna.socialmediaapi.authorization.model.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum name);
}
