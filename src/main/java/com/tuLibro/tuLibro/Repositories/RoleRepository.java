package com.tuLibro.tuLibro.Repositories;

import com.tuLibro.tuLibro.Entities.ERole;
import com.tuLibro.tuLibro.Entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,Long> {
    Optional<RoleEntity> findByRol(ERole role);
}
