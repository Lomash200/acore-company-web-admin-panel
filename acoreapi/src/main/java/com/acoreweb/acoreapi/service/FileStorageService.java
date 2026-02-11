// File: FileStorageService.java

package com.acoreweb.acoreapi.service;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.lang.NonNull; // Zaroori import

public interface FileStorageService {

    // YEH SIGNATURE SAHI HONA CHAHIYE (3 arguments)
    String storeFile(@NonNull MultipartFile file, @NonNull String subDirectory, @NonNull String baseFileName);

    void deleteFile(String filename);
}