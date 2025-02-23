package com.gleb.web.server;

import com.gleb.web.parser.FileManager;
import com.gleb.web.reader.InputReader;
import com.gleb.web.reader.InputReaderImpl;
import com.gleb.web.response.ResponseManager;
import com.gleb.web.response.ResponseManagerImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class WebServerImpl implements WebServer {

    private static final Logger logger = Logger.getLogger(WebServerImpl.class.getName());

    private final int PORT;
    private InputReader inputReader;
    private ResponseManager responseManager;


    public WebServerImpl() {
        this.PORT = 8080;
        this.inputReader = new InputReaderImpl();
        this.responseManager = new ResponseManagerImpl();
    }

    public WebServerImpl(int PORT) {
        this.PORT = PORT;
        this.inputReader = new InputReaderImpl();
        this.responseManager = new ResponseManagerImpl();
    }

    @Override
    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.info("Server started at: http://localhost:" + PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                String requestedFileName = inputReader.parseFilePath(socket);
                try {
                    File file = FileManager.getFile(requestedFileName);
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
        logger.info("Attempting to send file not found response...");
        try{
            String responseBody = FileManager.getFileNotFoundTemplate(requestedFileName);
            responseManager.sendFileNotFoundResponse(socket, responseBody);
        } catch (IOException e) {
            sendInternalErrorResponse(socket);
        }
    }

    private void sendInternalErrorResponse(Socket socket){
        logger.info("Attempting to send Internal Server Error Response...");
        try {
            File file = FileManager.getInternalErrorFile();
            responseManager.sendInternalServerErrorResponse(socket, file);

        } catch (IOException exception) {
            logger.severe("Unsuccessful 503 response attempt.");
            exception.printStackTrace();
        }
    }
}
