package com.smart.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smart.entities.User;

@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User,Integer> {
    boolean existsByname(String name);
   // boolean existsByEmail(String email);
   // Optional<User> findByName(String name);
   // @EntityGraph(attributePaths = {"contacts", "blogs"})    
   // @Query("SELECT u FROM User u WHERE u.email = :email")
   // Optional<User> findByEmail(@Param("email") String email);


    }
