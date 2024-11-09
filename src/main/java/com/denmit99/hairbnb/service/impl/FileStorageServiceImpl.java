package com.denmit99.hairbnb.service.impl;

import com.denmit99.hairbnb.model.entity.File;
import com.denmit99.hairbnb.repository.FileRepository;
import com.denmit99.hairbnb.service.FileStorageService;
import com.denmit99.hairbnb.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private static final String DEFAULT_FILE_NAME = "untitled";

    private final FileRepository fileRepository;

    private final UserService userService;

    public FileStorageServiceImpl(FileRepository fileRepository, UserService userService) {
        this.fileRepository = fileRepository;
        this.userService = userService;
    }

    @Override
    public void upload(MultipartFile file) throws IOException {
        var user = userService.getCurrent();
        String fileName = StringUtils.cleanPath(Optional.ofNullable(file.getOriginalFilename())
                .orElse(DEFAULT_FILE_NAME));
        File fileEntity = File.builder().name(fileName)
                .userId(user.getId())
                .creationDate(ZonedDateTime.now())
                .type(file.getContentType())
                .data(file.getBytes())
                .build();
        fileRepository.save(fileEntity);
    }
}
