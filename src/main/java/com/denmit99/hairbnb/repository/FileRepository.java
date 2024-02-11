package com.denmit99.hairbnb.repository;

import com.denmit99.hairbnb.model.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
