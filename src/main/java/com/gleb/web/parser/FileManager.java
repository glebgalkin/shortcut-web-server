package com.gleb.web.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class FileManager {

    private static final Logger logger = Logger.getLogger(FileManager.class.getName());
    private static final String DEFAULT_FILE = "index.html";
    private static final String INTERNAL_SERVER_ERROR = "503.html";
    private static final String FILE_NOT_FOUND = "404.html";
    private static final Path defaultPath = Paths.get("").toAbsolutePath().resolve("public/default").normalize();
    private static final Path filesPath = Paths.get("").toAbsolutePath().resolve("public/files").normalize();

    public static File getFile(String relativePath) throws FileNotFoundException {
        if (relativePath.equals("/") || relativePath.isEmpty()) return getDefaultFile();
        if (relativePath.startsWith("/")) relativePath = relativePath.substring(1);

        Path filePath = filesPath.resolve(relativePath).normalize();
        logger.info("Requested File Path: " + filePath);

        File file = filePath.toFile();
        validateFileExists(file);
        return file;
    }

    public static File getInternalErrorFile() throws FileNotFoundException{
        Path filePath = defaultPath.resolve(INTERNAL_SERVER_ERROR);
        File file = filePath.toFile();
        validateFileExists(file);
        return file;
    }

    public static String getFileNotFoundTemplate(String requestedFileName) throws IOException {
        Path filePath = defaultPath.resolve(FILE_NOT_FOUND).normalize();
        String htmlContent = Files.readString(filePath, StandardCharsets.UTF_8);
        return htmlContent.replace("{{FILENAME}}", requestedFileName);
    }

    private static File getDefaultFile() throws FileNotFoundException {
        logger.info("Default file is requested...");
        Path indexPath = defaultPath.resolve(DEFAULT_FILE).normalize();
        File file = indexPath.toFile();
        validateFileExists(file);
        return file;
    }

    private static void validateFileExists(File file) throws FileNotFoundException {
        if (!file.exists() || !file.isFile()) {
            throw new FileNotFoundException("File not found: " + file.getAbsolutePath());
        }
    }
}
