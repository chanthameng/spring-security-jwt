package com.kshrd.spring_security_jwt.repository;

import com.kshrd.spring_security_jwt.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

  Users findByUsername(String username);
}
