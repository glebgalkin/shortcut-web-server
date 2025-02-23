package com.gleb.web;

import com.gleb.web.server.WebServer;
import com.gleb.web.server.WebServerImpl;

import java.io.IOException;

public class WebServerApplication {
    public static void main(String[] args) throws IOException {
        WebServer webServer = new WebServerImpl();
        webServer.start();
    }
}
