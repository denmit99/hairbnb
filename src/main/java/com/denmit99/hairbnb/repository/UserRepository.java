package com.denmit99.hairbnb.repository;

import com.denmit99.hairbnb.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE public.user_info
            SET last_login_date = :dateTime
            WHERE id = :userId
            """, nativeQuery = true)
    void updateLastLoginDate(Long userId, ZonedDateTime dateTime);
}
