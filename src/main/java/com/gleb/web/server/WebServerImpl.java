package com.gleb.web.server;

import com.gleb.web.server.client.ClientHandler;
import com.gleb.web.server.client.ClientHandlerFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;

import static com.gleb.web.config.PrettyPrinter.getAppName;
import static com.gleb.web.config.PrettyPrinter.printBanner;

@Slf4j
class WebServerImpl implements WebServer {
    private final int port;
    private final Executor executor;
    public WebServerImpl(int port, Executor executor) {
        this.port = port;
        this.executor = executor;
    }

    @Override
    public void start() throws IOException {
        printBanner();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            log.info("{} started at: http://localhost:{}", getAppName(), port);
            while (true) {
                Socket socket = serverSocket.accept();
                executor.execute(() -> handleRequest(socket));
            }
        }
    }

    private void handleRequest(Socket socket){
        log.info("Running thread: {}", Thread.currentThread().getName());
        ClientHandler clientHandler = ClientHandlerFactory.create(socket);
        clientHandler.handleClient();
    }
}
