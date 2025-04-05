package com.gleb.web.server.client.handler;

import com.gleb.web.file.FileService;
import com.gleb.web.http.HttpHandler;
import com.gleb.web.http.request.HttpRequest;
import com.gleb.web.http.request.HttpMethod;
import com.gleb.web.http.response.HttpResponse;
import com.gleb.web.http.response.ResponseMapper;
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

    public ClientHandlerImpl(Socket socket, FileService fileService,
                             HttpHandler httpHandler) {
        this.socket = socket;
        this.httpHandler = httpHandler;
        this.fileService = fileService;
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
        HttpResponse response = null;
        if (isGetRequest(request)) {
            response = handleGetRequest(request);
        } else if (isPostRequest(request)) {
            response = handlePostRequest();
        }

        if (response != null) {
            httpHandler.sendResponse(response);
        }
    }

    private boolean isGetRequest(HttpRequest request) {
        return request.getMethod().equals(HttpMethod.GET);
    }

    private boolean isPostRequest(HttpRequest request) {
        return request.getMethod().equals(HttpMethod.POST);
    }


    private HttpResponse handleGetRequest(HttpRequest request) throws IOException {
        File file = fileService.getFile(request.getPath());
        return getHttpResponse(Status.OK, file);
    }

    private HttpResponse handlePostRequest() throws IOException {
        File file = fileService.getFileUploaded();
        return getHttpResponse(Status.OK, file);
    }

    private HttpResponse getHttpResponse(Status status, File file) throws IOException {
        ResponseMapper mapper = new ResponseMapper();
        return mapper.map(status, file);
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
            HttpResponse httpResponse = getHttpResponse(Status.NOT_FOUND, file);
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
            HttpResponse httpResponse = getHttpResponse(Status.SERVICE_UNAVAILABLE, file);
            httpHandler.sendResponse(httpResponse);
        } catch (IOException e) {
            log.error("I/O Error sending 503 response. Request might be lost. Error: {}", e.getMessage());
        }
    }
}
