package com.denmit99.hairbnb.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {
    void upload(MultipartFile file) throws IOException;
}
