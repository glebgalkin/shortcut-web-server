package com.gleb.web.server;

import com.gleb.web.file.FileManager;
import com.gleb.web.file.FileService;
import com.gleb.web.file.FileUtil;
import com.gleb.web.file.PathResolver;
import com.gleb.web.network.NetworkFactory;
import com.gleb.web.network.NetworkService;
import com.gleb.web.network.request.HttpRequest;
import com.gleb.web.network.request.RequestParser;
import com.gleb.web.network.response.ResponseManager;
import com.gleb.web.network.response.builder.ResponseBuilder;
import com.gleb.web.network.response.builder.Status;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.util.logging.Logger;

class WebServerImpl implements WebServer {

    private static final Logger logger = Logger.getLogger(WebServerImpl.class.getName());

    private final int port;

    public WebServerImpl(int port) {
        this.port = port;
    }

    @Override
    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("Server started at: http://localhost:" + port);

            while (true) {
                Socket socket = serverSocket.accept();
                try {
                    NetworkService networkService = NetworkFactory.getNetworkService(socket);
                    HttpRequest request = networkService.getRequest();
                    Path path = PathResolver.getFilePath(request.getPath());
                    File file = FileUtil.getFile(path);

                    byte[] response = ResponseBuilder.build(Status.OK, file);
                    networkService.sendResponse(response);

                } catch (FileNotFoundException e) {
                    logger.info("Could not find the file with requested path: " );
//                    sendFileNotFoundResponse(socket, requestedFileName);

                } catch (IOException e) {
                    logger.severe("I/O error occurred while handling request: " + e.getMessage());
                    e.printStackTrace();
                    sendInternalErrorResponse(socket);

                } finally {
                    socket.close();
                }
            }
        }
    }

    private void sendFileNotFoundResponse(Socket socket, String requestedFileName) throws IOException {
//        try {
//            String responseBody = fileManager.get404File(requestedFileName);
//            responseManager.sendFileNotFoundResponse(socket, responseBody);
//            logger.info("Sent File Not Found response.");
//        } catch (IOException e) {
//            sendInternalErrorResponse(socket);
//        }
    }

    private void sendInternalErrorResponse(Socket socket) {
//        try {
//            File file = fileManager.get503File();
//            responseManager.sendInternalServerErrorResponse(socket, file);
//            logger.info("Sent Internal Server Error Response.");
//
//        } catch (IOException exception) {
//            logger.severe("Unsuccessful 503 response attempt.");
//            exception.printStackTrace();
//        }
    }
}
