package com.gleb.web.server.server.file.parser;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {
    private static final String FILES_DIRECTORY = "files";

    public static File getFile(String relativePath){
        if (relativePath.startsWith("/")) {
            relativePath = relativePath.substring(1);
        }

        Path rootPath = Paths.get("").toAbsolutePath().resolve(FILES_DIRECTORY).normalize();
        Path filePath = rootPath.resolve(relativePath).normalize();
        System.out.println("Root Directory: " + rootPath);
        System.out.println("Requested File Path: " + filePath);
        File file = filePath.toFile();
        if (!file.exists() || !file.isFile()) {
            throw new RuntimeException("File not found: " + file.getAbsolutePath());
        }
        return file;
    }
}
