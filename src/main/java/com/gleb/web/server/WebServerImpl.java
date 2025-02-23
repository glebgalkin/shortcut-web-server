package com.gleb.web.server;

import com.gleb.web.file.manager.FileManager;
import com.gleb.web.request.RequestParser;
import com.gleb.web.response.ResponseManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

class WebServerImpl implements WebServer {

    private static final Logger logger = Logger.getLogger(WebServerImpl.class.getName());

    private final int PORT;
    private final RequestParser requestParser;
    private final ResponseManager responseManager;
    private final FileManager fileManager;

    public WebServerImpl(int PORT, RequestParser requestParser,
                         ResponseManager responseManager,
                         FileManager fileManager) {
        this.PORT = PORT;
        this.requestParser = requestParser;
        this.responseManager = responseManager;
        this.fileManager = fileManager;
    }

    @Override
    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.info("Server started at: http://localhost:" + PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                String requestedFileName = requestParser.parseFilePath(socket);
                try {
                    File file = fileManager.getFile(requestedFileName);
                    responseManager.sendFile(socket, file);

                } catch (FileNotFoundException e) {
                    logger.info("Could not find the file with requested path: " + requestedFileName);
                    sendFileNotFoundResponse(socket, requestedFileName);

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
        try{
            String responseBody = fileManager.get404File(requestedFileName);
            responseManager.sendFileNotFoundResponse(socket, responseBody);
            logger.info("Sent File Not Found response.");
        } catch (IOException e) {
            sendInternalErrorResponse(socket);
        }
    }

    private void sendInternalErrorResponse(Socket socket){
        try {
            File file = fileManager.get503File();
            responseManager.sendInternalServerErrorResponse(socket, file);
            logger.info("Sent Internal Server Error Response.");

        } catch (IOException exception) {
            logger.severe("Unsuccessful 503 response attempt.");
            exception.printStackTrace();
        }
    }
}
