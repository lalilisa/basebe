package com.example.chatapplication.service.fileservice;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FilesStorageService {
    void init();

    String save(MultipartFile file, String folderName);

    Resource load(String filename);

    void deleteAll();

    Stream<Path> loadAll();
}