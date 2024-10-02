package com.tuLibro.tuLibro.Repositories;

import com.tuLibro.tuLibro.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByUsername(String usermame);
    boolean existsByUsername(String username);
}
