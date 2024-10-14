package com.denmit99.hairbnb.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "file")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "data")
    private byte[] data;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "creation_date")
    private ZonedDateTime creationDate;
}
