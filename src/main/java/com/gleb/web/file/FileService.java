package com.gleb.web.file;

import java.io.File;
import java.nio.file.Path;

public interface FileService {

    File getFile(Path path);
}
