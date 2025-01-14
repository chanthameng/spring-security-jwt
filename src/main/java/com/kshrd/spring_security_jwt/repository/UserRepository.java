package com.kshrd.spring_security_jwt.repository;

import com.kshrd.spring_security_jwt.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Integer> {
    @Query("SELECT u FROM AppUser u WHERE u.email = :username")
    AppUser findByUsername(@Param("username") String username);
}
