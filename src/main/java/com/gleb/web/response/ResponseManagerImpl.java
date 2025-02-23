package com.gleb.web.response;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

public class ResponseManagerImpl implements ResponseManager {
    @Override
    public void sendFile(Socket socket, File file) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(outputStream, true);

        buildResponseStatus(writer, Status.OK);
        addHeader(writer, Header.CONTENT_TYPE.getName(), Header.ContentType.OCTET_STREAM.getName());
        addHeader(writer, Header.CONTENT_LENGTH.getName(), String.valueOf(file.length()));
        addNewLine(writer);
        addFileToResponse(file, outputStream);
        sendResponse(outputStream);
    }

    @Override
    public void sendFileNotFoundResponse(Socket socket, String body) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(outputStream, true);

        buildResponseStatus(writer, Status.NOT_FOUND);
        addHeader(writer, Header.CONTENT_TYPE.getName(), Header.ContentType.TEXT_HTML.getName());
        addNewLine(writer);
        addStringToResponse(body, writer);
        sendResponse(outputStream);
    }

    @Override
    public void sendInternalServerErrorResponse(Socket socket, File file) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(outputStream, true);

        buildResponseStatus(writer, Status.SERVICE_UNAVAILABLE);
        addHeader(writer, Header.CONTENT_TYPE.getName(), Header.ContentType.TEXT_HTML.getName());
        addNewLine(writer);
        addFileToResponse(file, outputStream);
        sendResponse(outputStream);
    }

    private void addHeader(PrintWriter writer, String key, String value) {
        writer.println(String.format("%s: %s", key, value));
    }

    private void addNewLine(PrintWriter writer) {
        writer.println();
    }

    private void buildResponseStatus(PrintWriter writer, Status status) {
        writer.write(status.getStatus());
    }

    private void sendResponse(OutputStream outputStream) throws IOException {
        outputStream.flush();
    }

    private void addFileToResponse(File file, OutputStream outputStream) throws IOException {
        Files.copy(file.toPath(), outputStream);
    }

    private void addStringToResponse(String responseBody, PrintWriter writer){
        writer.println(responseBody);
    }
}
