package com.gleb.web.server;

import com.gleb.web.server.client.ClientHandler;
import com.gleb.web.server.client.ClientHandlerFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
class WebServerImpl implements WebServer {
    private final int port;
    public WebServerImpl(int port) {
        this.port = port;
    }

    @Override
    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            log.info("Server started at: http://localhost:{}", port);

            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = ClientHandlerFactory.create(socket);
                clientHandler.handleClient();
            }
        }
    }
}
