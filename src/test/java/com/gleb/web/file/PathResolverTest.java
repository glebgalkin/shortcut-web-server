package com.gleb.web.file;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PathResolverTest {

    @Test
    public void getDefaultFilePath() {
        // when
        Path path = PathResolver.getFilePath("/");

        // then
        assertTrue(path.endsWith("public/default/index.html"));
    }

    @Test
    public void getDefaultFilePathWithQueryParams() {
        // when
        Path path = PathResolver.getFilePath("/?someKey=someVal");

        // then
        assertTrue(path.endsWith("public/default/index.html"));
    }

    @Test
    public void getCorrectFilePath() {
        // when
        Path path = PathResolver.getFilePath("/picture.jpg");

        // then
        assertTrue(path.endsWith("public/files/picture.jpg"));
    }

    @Test
    void getFileNotFoundPath() {
        // when
        Path path = PathResolver.getFileNotFoundPath();

        // then
        assertTrue(path.endsWith("public/default/404.html"));
    }

    @Test
    void getInternalErrorPath() {
        // when
        Path path = PathResolver.getInternalServerErrorPath();

        // then
        assertTrue(path.endsWith("public/default/503.html"));
    }
}
