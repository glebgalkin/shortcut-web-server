package com.gleb.web.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;


public class FileUtil {

    public static File getFile(Path path) throws FileNotFoundException {
        validateFileExist(path);
        return path.toFile();
    }

    private static void validateFileExist(Path path) throws FileNotFoundException {
        if (path == null || !Files.exists(path)) {
            throw new FileNotFoundException("File does not exist: " + path);
        }
    }
}
