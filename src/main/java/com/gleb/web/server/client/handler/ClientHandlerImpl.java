package com.gleb.web.server.client.handler;

import com.gleb.web.file.FileService;
import com.gleb.web.http.HttpHandler;
import com.gleb.web.http.request.HttpRequest;
import com.gleb.web.http.response.HttpResponse;
import com.gleb.web.http.response.HttpResponseFactory;
import com.gleb.web.http.response.Status;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;

@Slf4j
public class ClientHandlerImpl implements ClientHandler {

    private final Socket socket;
    private final HttpHandler httpHandler;
    private final FileService fileService;
    private final HttpResponseFactory httpResponseFactory;

    public ClientHandlerImpl(Socket socket, FileService fileService,
                             HttpHandler httpHandler,
                             HttpResponseFactory httpResponseFactory) {
        this.socket = socket;
        this.httpHandler = httpHandler;
        this.fileService = fileService;
        this.httpResponseFactory = httpResponseFactory;
    }

    @Override
    public void handleClient() {
        try {
            handleRequest();

        } catch (FileNotFoundException e) {
            log.info(e.getMessage());
            sendFileNotFoundResponse();

        } catch (IOException e) {
            log.error("I/O error occurred while handling request: {}", e.getMessage());
            sendInternalErrorResponse();

        } finally {
            closeSocketConnection();
        }
    }

    private void handleRequest() throws IOException {
        HttpRequest request = httpHandler.getRequest();
        File file = fileService.getFile(request.getPath());
        HttpResponse httpResponse = httpResponseFactory.build(Status.OK, file);

        httpHandler.sendResponse(httpResponse);
    }

    private void closeSocketConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            log.error("I/O Error closing socket: {}", e.getMessage());
        }
    }

    private void sendFileNotFoundResponse() {
        try {
            File file = fileService.getFileNotFound();
            HttpResponse httpResponse = httpResponseFactory.build(Status.NOT_FOUND, file);
            httpHandler.sendResponse(httpResponse);
        } catch (IOException e) {
            log.error("I/O Error sending 404 response error: {}", e.getMessage());
            log.info("Sending 503 response.");
            sendInternalErrorResponse();
        }
    }

    private void sendInternalErrorResponse() {
        try {
            File file = fileService.getInternalServerError();
            HttpResponse httpResponse = httpResponseFactory.build(Status.SERVICE_UNAVAILABLE, file);
            httpHandler.sendResponse(httpResponse);
        } catch (IOException e) {
            log.error("I/O Error sending 503 response. Request might be lost. Error: {}", e.getMessage());
        }
    }
}
