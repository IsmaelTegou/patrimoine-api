package com.ktiservice.patrimoine.utils;

import com.ktiservice.patrimoine.exceptions.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

/**
 * Utility class for file validation and processing.
 * Validates file size, type, and format.
 */
@Slf4j
@Component
public class FileUtil {

    @Value("${app.file-upload.max-file-size}")
    private long maxPhotoSize;

    @Value("${app.file-upload.max-video-size}")
    private long maxVideoSize;

    @Value("${app.file-upload.allowed-photo-formats}")
    private String allowedPhotoFormats;

    @Value("${app.file-upload.allowed-video-formats}")
    private String allowedVideoFormats;

    /**
     * Validate photo file.
     *
     * @param file The photo file
     */
    public void validatePhotoFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ValidationException("File cannot be empty");
        }

        // Check file size
        if (file.getSize() > maxPhotoSize) {
            throw new ValidationException(
                    String.format("Photo size exceeds maximum limit of %d MB", maxPhotoSize / 1024 / 1024)
            );
        }

        // Check file extension
        String fileName = file.getOriginalFilename();
        String extension = getFileExtension(fileName).toLowerCase();

        if (!Arrays.asList(allowedPhotoFormats.split(",")).contains(extension)) {
            throw new ValidationException(
                    String.format("Allowed photo formats are: %s", allowedPhotoFormats)
            );
        }

        // Check MIME type
        String contentType = file.getContentType();
        if (!contentType.startsWith("image/")) {
            throw new ValidationException("File must be a valid image");
        }
    }

    /**
     * Validate video file.
     *
     * @param file The video file
     */
    public void validateVideoFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ValidationException("File cannot be empty");
        }

        // Check file size
        if (file.getSize() > maxVideoSize) {
            throw new ValidationException(
                    String.format("Video size exceeds maximum limit of %d MB", maxVideoSize / 1024 / 1024)
            );
        }

        // Check file extension
        String fileName = file.getOriginalFilename();
        String extension = getFileExtension(fileName).toLowerCase();

        if (!Arrays.asList(allowedVideoFormats.split(",")).contains(extension)) {
            throw new ValidationException(
                    String.format("Allowed video formats are: %s", allowedVideoFormats)
            );
        }

        // Check MIME type
        String contentType = file.getContentType();
        if (!contentType.startsWith("video/")) {
            throw new ValidationException("File must be a valid video");
        }
    }

    /**
     * Extract file extension from filename.
     */
    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * Generate a safe filename with UUID prefix.
     */
    public String generateSafeFileName(String originalFileName) {
        String extension = getFileExtension(originalFileName);
        String sanitizedName = originalFileName
                .replaceAll("[^a-zA-Z0-9._-]", "_")
                .replaceAll("_+", "_");
        return java.util.UUID.randomUUID() + "_" + sanitizedName;
    }
}
