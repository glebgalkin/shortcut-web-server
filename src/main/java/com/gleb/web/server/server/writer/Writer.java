package com.gleb.web.server.server.writer;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

public interface Writer {
    void sendFile(Socket socket, File file) throws IOException;
}
