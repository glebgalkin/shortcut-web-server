package com.gleb.web.file;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;


public class FileUtil {

    public static File getFile(Path path) {
        validateFileExist(path);
        return path.toFile();
    }

    private static void validateFileExist(Path path) {
        if (path == null || !Files.exists(path)) {
            throw new IllegalArgumentException("File does not exist: " + path);
        }
    }
}
