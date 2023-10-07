package com.searcher.searcher.controller;

import com.searcher.searcher.entity.FileData;
import com.searcher.searcher.repository.FileStorageRepository;
import com.searcher.searcher.service.SolrService;
import com.searcher.searcher.service.StorageService;
import jakarta.annotation.Resource;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/")
public class StorageController {

    @Resource
    private SolrService solrService;

    @Resource
    private FileStorageRepository fileStorageRepository;

    @Resource
    private StorageService storageService;

    @GetMapping("/search")
    public String search(@RequestParam(name = "adminMode", required = false, defaultValue = "false") boolean adminMode, @RequestParam(name = "value", required = false) String value, Model model) {
        if(value != null) {
            List<FileData> result = fileStorageRepository.getFileDataByIdentifiers(solrService.searchByString(value));
            model.addAttribute("searchResult", result);
        }
        model.addAttribute("value", value != null ? value : "");
        model.addAttribute("adminMode", adminMode);
        return "search";
    }

    @PostMapping("/upload")
    public String uploadFiles(@RequestParam("files") List<MultipartFile> files) {
        storageService.uploadFiles(files);
        return "redirect:/search";
    }

    @GetMapping("/{id}")
    public String showFile(@RequestParam(name = "adminMode", required = false, defaultValue = "false") boolean adminMode, @PathVariable Long id, Model model) throws IOException {
        FileData file = fileStorageRepository.getById(id);
        ClassPathResource resource = new ClassPathResource("/files/" + file.getIdentifier());
        InputStream inputStream = resource.getInputStream();

        byte[] pdfBytes = IOUtils.toByteArray(inputStream);

        String base64Encoded = Base64.getEncoder().encodeToString(pdfBytes);

        model.addAttribute("adminMode", adminMode);
        model.addAttribute("file", file);
        model.addAttribute("displayFile", base64Encoded);
        return "fileProfile";
    }

    @PostMapping("/remove/{id}")
    public String removeFile(@PathVariable Long id){
        storageService.removeFile(id);
        return "redirect:/search";
    }

}
