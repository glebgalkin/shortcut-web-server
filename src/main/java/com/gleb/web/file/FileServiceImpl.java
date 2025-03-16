package com.gleb.web.file;

import com.gleb.web.config.ConfigLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileServiceImpl implements FileService {
    private final PathResolver pathResolver;

    public FileServiceImpl() {
        this.pathResolver = new PathResolver();
    }

    @Override
    public File getFile(String filePath) throws FileNotFoundException {
        Path path = pathResolver.getFilePath(filePath);
        validateFileExist(path);
        return path.toFile();
    }

    @Override
    public File getFileNotFound() {
        return pathResolver.getFileNotFoundPath().toFile();
    }

    @Override
    public File getInternalServerError() {
        return pathResolver.getInternalServerErrorPath().toFile();
    }

    private static void validateFileExist(Path path) throws FileNotFoundException {
        if (path == null || !Files.exists(path)) {
            throw new FileNotFoundException("Could not find the file with requested path: " + path);
        }
    }

    private class PathResolver {
        private final Path root;
        private final Path filesDirectory;
        private final Path defaultPath;
        private final Path notFoundPath;
        private final Path internalErrorPath;

        public PathResolver() {
            this.root = Paths.get("").toAbsolutePath();
            this.filesDirectory = root.resolve(Paths.get(ConfigLoader.get("directory.files")));
            this.defaultPath = root.resolve(Paths.get(ConfigLoader.get("directory.default")));
            this.notFoundPath = root.resolve(Paths.get(ConfigLoader.get("directory.not.found")));
            this.internalErrorPath = root.resolve(Paths.get(ConfigLoader.get("directory.internal.error")));
        }

        public Path getFilePath(String requestPath) {
            String path = filterPath(requestPath);
            if (isDefaultPath(path)) return defaultPath;
            return getRequestedFilePath(path);
        }

        public Path getFileNotFoundPath() {
            return notFoundPath;
        }

        public Path getInternalServerErrorPath() {
            return internalErrorPath;
        }

        private Path getRequestedFilePath(String path) {
            Path requestedPath = Paths.get(path).normalize();

            if (requestedPath.isAbsolute()) {
                requestedPath = Paths.get(requestedPath.toString().substring(1)); // Remove leading "/"
            }

            return filesDirectory.resolve(requestedPath).toAbsolutePath().normalize();
        }


        private boolean isDefaultPath(String path) {
            return path.isEmpty() || path.equals("/");
        }

        private String filterPath(String requestPath) {
            return requestPath.split("\\?")[0];
        }
    }
}
