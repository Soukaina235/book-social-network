package com.soukaina.book.file;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.io.File.separator;
import static java.lang.System.currentTimeMillis;

@Service
@RequiredArgsConstructor
@Slf4j // for logs
public class FileStorageService {

    @Value("${application.file.upload.photos-output-path}")
    private String fileUploadPath;

    /**
     *
     * @param sourceFile
     * @param userId
     * @return the final path of the saved file
     */
    public String saveFile(
            @NonNull MultipartFile sourceFile,
            @NonNull Integer userId
    ) {
        // we are going to create a sub-part for each user
        // the separator depends on the system, it can be / or \
        // so using File.separator does this depending on the system
        final String fileUploadSubPath = "users" + separator + userId;
        return uploadFile(sourceFile, fileUploadSubPath);
    }

    private String uploadFile(
            @NonNull MultipartFile sourceFile,
            @NonNull String fileUploadSubPath
    ) {
        final String finalUploadPath = fileUploadPath + separator + fileUploadSubPath;
        File targetFolder = new File(finalUploadPath);

        // create the folder if it doesn't exist
        if (!targetFolder.exists()) {
            // targetFolder returns true if created, false if not
            boolean folderCreated = targetFolder.mkdirs();
            if (!folderCreated) {
                log.warn("Failed to create the target folder");
                return null;
            }
        }

        // getOriginalFilename() returns the full name of the file
        final String fileExtension = getFileExtension(sourceFile.getOriginalFilename());
        // for exp: ./uploads/users/1/645425424234.jpg
        String targetFilePath = finalUploadPath + separator + currentTimeMillis() + "." + fileExtension;
        Path targetPath = Paths.get(targetFilePath);
        try {
            // writes the bytes of the sourceFile to the targetPath location
            Files.write(targetPath, sourceFile.getBytes());
            log.info("File saved to: {}", targetFilePath);
            return targetFilePath;
        } catch (IOException e) {
            log.error("File was not saved", e);
        }
        return null;
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int lastDotIndex = fileName.lastIndexOf('.');
        // if the file doesn't have an extension
        if (lastDotIndex == -1) {
            return "";
        }
        // toLowerCase in case we receive something like .JPG
        return fileName.substring(lastDotIndex + 1).toLowerCase();
    }
}
