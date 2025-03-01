package com.gleb.web.file;

import com.gleb.web.config.ConfigLoader;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathResolver {

    public static Path getFilePath(String requestPath){
        String path = filterPath(requestPath);
        if(isDefaultPath(path)) return getDefaultPath();
        return getRequestedFilePath(path);
    }

    public static Path getFileNotFoundPath(){
        return Paths.get("").toAbsolutePath().resolve(ConfigLoader.get("directory.not.found"));
    }

    public static Path getInternalServerErrorPath(){
        return Paths.get("").toAbsolutePath().resolve(ConfigLoader.get("directory.internal.error"));
    }

    private static Path getRequestedFilePath(String path){
        return Paths.get(ConfigLoader.get("directory.files"), path).toAbsolutePath().normalize();
    }

    private static boolean isDefaultPath(String path){
        return path.isEmpty() || path.equals("/");
    }

    private static Path getDefaultPath(){
        return Paths.get("").toAbsolutePath().resolve(ConfigLoader.get("directory.default"));
    }

    private static String filterPath(String requestPath){
        return requestPath.split("\\?")[0];
    }
}
