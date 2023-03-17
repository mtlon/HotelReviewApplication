package com.api.hotelreviewapplication.repository;

import com.api.hotelreviewapplication.model.UserEntity;
import com.api.hotelreviewapplication.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByUsername(String username);
    Boolean existsByUsername(String username);
}
