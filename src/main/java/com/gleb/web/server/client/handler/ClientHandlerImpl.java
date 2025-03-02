package com.gleb.web.server.client.handler;

import com.gleb.web.file.FileUtil;
import com.gleb.web.file.PathResolver;
import com.gleb.web.network.NetworkFactory;
import com.gleb.web.network.NetworkService;
import com.gleb.web.network.request.HttpRequest;
import com.gleb.web.network.response.HttpResponse;
import com.gleb.web.network.response.HttpResponseBuilder;
import com.gleb.web.network.response.Status;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Path;

@Slf4j
public class ClientHandlerImpl implements ClientHandler {

    private final Socket socket;
    private final NetworkService networkService;

    public ClientHandlerImpl(Socket socket) {
        this.socket = socket;
        this.networkService = NetworkFactory.getNetworkService(socket);
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
        HttpRequest request = networkService.getRequest();
        Path path = PathResolver.getFilePath(request.getPath());
        File file = FileUtil.getFile(path);

        HttpResponse httpResponse = HttpResponseBuilder.build(Status.OK, file);
        networkService.sendResponse(httpResponse);
    }

    private void closeSocketConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            log.error("I/O Error closing socket: {}", e.getMessage());
        }
    }

    private void sendFileNotFoundResponse() {
        Path path = PathResolver.getFileNotFoundPath();
        try {
            File file = FileUtil.getFile(path);
            HttpResponse httpResponse = HttpResponseBuilder.build(Status.NOT_FOUND, file);
            networkService.sendResponse(httpResponse);
        } catch (IOException e) {
            log.error("I/O Error sending 404 response with path: {}, error: {}", path, e.getMessage());
            log.info("Sending 503 response.");
            sendInternalErrorResponse();
        }
    }

    private void sendInternalErrorResponse() {
        try {
            Path path = PathResolver.getInternalServerErrorPath();
            File file = FileUtil.getFile(path);
            HttpResponse httpResponse = HttpResponseBuilder.build(Status.SERVICE_UNAVAILABLE, file);
            networkService.sendResponse(httpResponse);
        } catch (IOException e) {
            log.error("I/O Error sending 503 response. Request might be lost. Error: {}", e.getMessage());
        }
    }
}
