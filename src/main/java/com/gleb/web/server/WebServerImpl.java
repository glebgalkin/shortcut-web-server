package com.gleb.web.server;

import com.gleb.web.http.util.StringUtil;
import com.gleb.web.server.client.handler.ClientHandler;
import com.gleb.web.server.client.ClientHandlerFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;

import static com.gleb.web.config.PrettyPrinter.*;

@Slf4j
class WebServerImpl implements WebServer {
    private final int port;
    private final String bindAddress;
    private final Executor executor;

    public WebServerImpl(int port, String bindAddress, Executor executor) {
        this.port = port;
        this.bindAddress = bindAddress;
        this.executor = executor;
    }

    @Override
    public void start() throws IOException {
        logBanner();
        try (ServerSocket serverSocket = getServerSocket()) {
            logSuccessfulStart(bindAddress, port);
            while (true) {
                Socket socket = serverSocket.accept();
                executor.execute(() -> handleRequest(socket));
            }
        }
    }

    private ServerSocket getServerSocket() throws IOException {
        return new ServerSocket(port, 50,
                StringUtil.parseIpString(bindAddress));
    }

    private void handleRequest(Socket socket) {
        log.info("Running thread: \u001B[31m{}\u001B[0m", Thread.currentThread().getName());
        ClientHandler clientHandler = ClientHandlerFactory.create(socket);
        clientHandler.handleClient();
    }
}
