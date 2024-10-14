package com.denmit99.hairbnb.repository;

import com.denmit99.hairbnb.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE public.user_info
            SET last_login_date = :dateTime
            WHERE id = :userId
            """, nativeQuery = true)
    void updateLastLoginDate(UUID userId, ZonedDateTime dateTime);
}
