package com.gleb.web;

import com.gleb.web.server.WebServer;
import com.gleb.web.server.WebServerFactory;

import java.io.IOException;

public class WebServerApplication {
    public static void main(String[] args) throws IOException {
        WebServer webServer = WebServerFactory.create();
        webServer.start();
    }
}
