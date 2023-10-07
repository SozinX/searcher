package com.searcher.searcher.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StorageService {
    void uploadFiles(List<MultipartFile> files);

    void removeFile(Long id);
}
