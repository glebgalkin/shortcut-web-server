package com.gleb.web.server.server.writer;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;

public class WriterImpl implements Writer{
    @Override
    public void sendFile(Socket socket, File file) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(outputStream, true);

        // 1️⃣ Send HTTP headers
        writer.println("HTTP/1.1 200 OK");
        writer.println("Content-Type: application/octet-stream");
        writer.println("Content-Length: " + file.length());
        writer.println();

        // 2️⃣ Send the file content in one go
        Files.copy(file.toPath(), outputStream);
        outputStream.flush();
    }
}
