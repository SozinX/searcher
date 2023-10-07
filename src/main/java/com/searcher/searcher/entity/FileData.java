package com.searcher.searcher.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name="FileData")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FileData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   private String fileName;

   private String filePath;

   private String fileType;

   private String identifier;

   private Long size;

   private Date createdDate;

   private boolean deleted;

}
