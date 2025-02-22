package com.gleb.web.server.server.reader;

import java.io.IOException;
import java.net.Socket;

public interface InputReader {

    String parseFilePath(Socket socket) throws IOException;
}
