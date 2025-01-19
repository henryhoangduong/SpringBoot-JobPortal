package com.henry.jobportal.util;

import org.springframework.security.core.parameters.P;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {
    public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = multipartFile.getInputStream();) {
            Path path = uploadPath.resolve(fileName);
            System.out.println("FilePath: " + path);
            System.out.println("filename: " + fileName);
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioException) {
            throw new IOException("Could not save image file: " + fileName, ioException);
        }
    }
}
