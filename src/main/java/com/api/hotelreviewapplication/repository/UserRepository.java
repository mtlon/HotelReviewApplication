package com.api.hotelreviewapplication.repository;

import com.api.hotelreviewapplication.model.UserEntity;
import com.api.hotelreviewapplication.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    @Query("SELECT u FROM UserEntity u WHERE u.username = :username")
    UserEntity getUserByUsername(@Param("username") String username);
    Boolean existsByUsername(String username);
}
