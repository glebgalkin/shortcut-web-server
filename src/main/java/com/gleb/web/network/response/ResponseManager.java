package com.gleb.web.network.response;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

public interface ResponseManager {
    void sendFile(Socket socket, File file) throws IOException;

    void sendFileNotFoundResponse(Socket socket, String body) throws IOException;

    void sendInternalServerErrorResponse(Socket socket, File file) throws IOException;
}
