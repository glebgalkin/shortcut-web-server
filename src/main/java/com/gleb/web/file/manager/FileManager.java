package com.gleb.web.file.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileManager {
    File getFile(String relativePath) throws FileNotFoundException;
    File get503File() throws FileNotFoundException;
    String get404File(String requestedFileName) throws IOException;
}
