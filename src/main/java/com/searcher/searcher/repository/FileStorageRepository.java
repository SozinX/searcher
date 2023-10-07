package com.searcher.searcher.repository;

import com.searcher.searcher.entity.FileData;
import jakarta.annotation.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Resource
public interface FileStorageRepository extends JpaRepository<FileData, Long> {
    @Query("SELECT f FROM FileData f WHERE f.id = :id AND f.deleted = false")
    FileData getById(@Param("id") Long id);

    @Query("SELECT f FROM FileData f WHERE f.identifier IN (:identifiers) AND f.deleted = false")
    List<FileData> getFileDataByIdentifiers(@Param("identifiers") List<String> identifiers);
}
