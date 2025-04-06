package com.gleb.web.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {
    File getFile(String filePath) throws FileNotFoundException;

    File getFileUploaded() throws FileNotFoundException;

    File getFileNotFound();
    File getInternalServerError();

    File buildFileFromStream(String fileName, InputStream inputStream, int contentLength) throws IOException;
}
