package com.gleb.web;

import com.gleb.web.server.WebServer;
import com.gleb.web.server.WebServerBuilder;

import java.io.IOException;
import java.util.concurrent.Executors;

public class WebServerApplication {
    public static void main(String[] args) throws IOException {
        WebServer webServer = new WebServerBuilder()
                .setPort(9090)
                .setBindAddress("0.0.0.0")
                .setExecutorService(Executors.newVirtualThreadPerTaskExecutor())
                .build();
        webServer.start();
    }
}
