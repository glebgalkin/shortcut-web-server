package com.gleb.web;

import com.gleb.web.server.WebServer;
import com.gleb.web.server.WebServerFactory;

import java.io.IOException;

public class WebServerApplication {
    public static void main(String[] args) throws IOException {

        // TODO all those managers and parsers shall not be avaliable on this level
        WebServer webServer = WebServerFactory.create(8080);

        webServer.start();
    }
}
