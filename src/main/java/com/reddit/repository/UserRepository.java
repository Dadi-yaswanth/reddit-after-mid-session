package com.reddit.repository;


import com.reddit.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.username = :username AND u.password = :password")
    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    User findByUserId(Long userId);

    Optional<User> findByUsernameIgnoreCase(String username);

    @Query("SELECT DISTINCT u FROM User u WHERE " +
            "(u.username ILIKE %:word%) OR (u.displayName ILIKE %:word%)")
    Page<User> findUsersBySearchName(@Param("word") String keyword, Pageable pageable);

    User findByUsername(String name);
}