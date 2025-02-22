package com.gleb.web.server;

import com.gleb.web.server.server.WebServer;
import com.gleb.web.server.server.WebServerImpl;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        WebServer webServer = new WebServerImpl();
        webServer.start();
    }
}
