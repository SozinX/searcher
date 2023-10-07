package com.searcher.searcher.service;

import com.searcher.searcher.entity.FileData;
import com.searcher.searcher.repository.FileStorageRepository;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class StorageServiceImpl implements StorageService{

    @Value("${file.upload-dir}")
    private String uploadDir;
    @Resource
    private FileStorageRepository fileStorageRepository;
    @Resource
    private SolrService solrService;

    @Override
    public void uploadFiles(List<MultipartFile> files) {
        try {
            if (files.isEmpty()) {
                System.out.println("Choose at least one file to upload.");
            }

            for (MultipartFile file : files) {
                String identifier = UUID.randomUUID().toString();
                String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
                String filePath = uploadDir + identifier;
                String fileType = file.getContentType();
                Long size = file.getSize();

                Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

                setDefaultFileValuesAndSave(identifier, fileName, filePath, fileType, size);

                solrService.indexFile(filePath, identifier, fileName);
            }
            System.out.println("Files uploaded successfully.");
        } catch (IOException e) {
            System.out.println("File upload failed. " + e.getMessage());
        }
    }

    @Override
    public void removeFile(Long id){
        FileData fileToRemove = fileStorageRepository.getById(id);
        fileToRemove.setDeleted(true);
        fileStorageRepository.save(fileToRemove);
    }

    private void setDefaultFileValuesAndSave(String identifier, String fileName, String filePath, String fileType, Long size){
        FileData fileInfo = new FileData();
        fileInfo.setFileName(fileName);
        fileInfo.setFilePath(filePath);
        fileInfo.setFileType(fileType);
        fileInfo.setIdentifier(identifier);
        fileInfo.setSize(size);
        fileInfo.setCreatedDate(new Date());
        fileInfo.setDeleted(false);
        fileStorageRepository.save(fileInfo);
    }
}
