package com.gleb.web.server.client;

import com.gleb.web.file.FileUtil;
import com.gleb.web.file.PathResolver;
import com.gleb.web.network.NetworkFactory;
import com.gleb.web.network.NetworkService;
import com.gleb.web.network.request.HttpRequest;
import com.gleb.web.network.response.ResponseBuilder;
import com.gleb.web.network.response.util.Status;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Path;

@Slf4j
public class ClientHandlerImpl implements ClientHandler {

    private final Socket socket;

    public ClientHandlerImpl(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void handleClient() {
        NetworkService networkService = NetworkFactory.getNetworkService(socket);
        try {
            handleRequest(networkService);

        } catch (FileNotFoundException e) {
            log.info(e.getMessage());
            sendFileNotFoundResponse(networkService);

        } catch (IOException e) {
            log.error("I/O error occurred while handling request: {}", e.getMessage());
            sendInternalErrorResponse(networkService);

        } finally {
            closeSocketConnection();
        }
    }

    private void handleRequest(NetworkService networkService) throws IOException {
        HttpRequest request = networkService.getRequest();
        Path path = PathResolver.getFilePath(request.getPath());
        File file = FileUtil.getFile(path);

        byte[] response = ResponseBuilder.build(Status.OK, file);
        networkService.sendResponse(response);
    }

    private void closeSocketConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            log.error("I/O Error closing socket: {}", e.getMessage());
        }
    }

    private void sendFileNotFoundResponse(NetworkService networkService) {
        Path path = PathResolver.getFileNotFoundPath();
        try {
            File file = FileUtil.getFile(path);
            byte[] response = ResponseBuilder.build(Status.NOT_FOUND, file);
            networkService.sendResponse(response);
        } catch (IOException e) {
            log.error("I/O Error sending 404 response with path: {}, error: {}", path, e.getMessage());
            log.info("Sending 503 response.");
            sendInternalErrorResponse(networkService);
        }
    }

    private void sendInternalErrorResponse(NetworkService networkService) {
        try {
            Path path = PathResolver.getInternalServerErrorPath();
            File file = FileUtil.getFile(path);
            byte[] response = ResponseBuilder.build(Status.SERVICE_UNAVAILABLE, file);
            networkService.sendResponse(response);
        } catch (IOException e) {
            log.error("I/O Error sending 503 response: {}", e.getMessage());
        }
    }
}
