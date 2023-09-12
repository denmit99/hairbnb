package com.denmit99.hairbnb.repository;

import com.denmit99.hairbnb.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
