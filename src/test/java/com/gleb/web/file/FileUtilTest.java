package com.gleb.web.file;

import com.gleb.web.config.ConfigLoader;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileUtilTest {

    @Test
    public void getExistingFile() throws FileNotFoundException {
        // given
        Path path = Paths.get("").toAbsolutePath().resolve(ConfigLoader.get("directory.default"));

        // when
        File file = FileUtil.getFile(path);

        // then
        assertTrue(file.exists());
        assertTrue(file.isFile());
        assertTrue(file.getPath().contains("/public/default/index.html"));
    }

    @Test
    public void catchErrorOfWrongPath(){
        // given
        Path path = Paths.get("").toAbsolutePath().resolve("/invalid/path");

        // when & then
        assertThrows(FileNotFoundException.class, () -> {
            FileUtil.getFile(path);
        });
    }


}
