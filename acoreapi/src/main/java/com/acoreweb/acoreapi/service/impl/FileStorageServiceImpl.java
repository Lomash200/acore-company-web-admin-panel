package com.acoreweb.acoreapi.service.impl;

import com.acoreweb.acoreapi.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageServiceImpl(@Value("${file.upload-dir}") String uploadDir) {

        // Windows path normalize
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();

        System.out.println("📁 Root Upload DIR = " + this.fileStorageLocation);

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("❌ Cannot create upload root directory!", ex);
        }
    }

    @Override
    public String storeFile(@NonNull MultipartFile file,
                            @NonNull String subDirectory,
                            @NonNull String baseFileName) {

        if (file.isEmpty()) {
            throw new RuntimeException("❌ Uploaded file is empty!");
        }

        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

        String cleanBaseName = StringUtils.cleanPath(baseFileName).replaceAll("[^A-Za-z0-9]", "_");
        String uniqueFileName = cleanBaseName + "_" + UUID.randomUUID() + extension;

        // Final folder path
        Path finalDir = this.fileStorageLocation.resolve(subDirectory).normalize();

        System.out.println("📁 Final Dir path = " + finalDir);

        try {
            Files.createDirectories(finalDir); // ensure folder exists
        } catch (IOException e) {
            throw new RuntimeException("❌ Cannot create directory: " + finalDir, e);
        }

        // Full image save location
        Path targetLocation = finalDir.resolve(uniqueFileName);

        System.out.println("📸 Saving FILE TO = " + targetLocation.toString());

        try {
            Files.copy(
                    file.getInputStream(),
                    targetLocation,
                    StandardCopyOption.REPLACE_EXISTING
            );
            System.out.println("✅ FILE SAVED SUCCESSFULLY!");
        } catch (Exception e) {
            System.err.println("❌ FILE SAVE FAILED: " + e.getMessage());
            throw new RuntimeException("Could not save file " + uniqueFileName, e);
        }

        // Return path stored in DB
        return subDirectory + "/" + uniqueFileName;
    }

    @Override
    public void deleteFile(String filename) {
        try {
            Path filePath = this.fileStorageLocation.resolve(filename).normalize();
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            System.err.println("❌ Delete file failed: " + e.getMessage());
        }
    }
}
