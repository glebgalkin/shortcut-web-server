package com.gleb.web.file;

import java.io.File;
import java.io.FileNotFoundException;

public interface FileService {
    File getFile(String filePath) throws FileNotFoundException;
    File getFileNotFound();
    File getInternalServerError();
}
