package com.denmit99.hairbnb.repository;

import com.denmit99.hairbnb.model.entity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface AmenityRepository extends JpaRepository<Amenity, Long> {
    @Query("Select a FROM Amenity a WHERE a.code IN :codes")
    List<Amenity> getAllByCodes(@Param("codes") Set<String> codes);

    //TODO
//    List<Amenity> getAllNonExistingCodes(@Param("codes") Set<String> codes);
}
