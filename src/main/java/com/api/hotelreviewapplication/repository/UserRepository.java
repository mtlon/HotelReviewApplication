package com.api.hotelreviewapplication.repository;

import com.api.hotelreviewapplication.model.UserEntity;
import com.api.hotelreviewapplication.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsername(String username);
    Boolean existsByUsername(String username);
}
