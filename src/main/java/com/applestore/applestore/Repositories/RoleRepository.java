package com.applestore.applestore.Repositories;

import com.applestore.applestore.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@EnableJpaRepositories
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}