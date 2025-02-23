package com.gleb.web;

import com.gleb.web.file.manager.FileManagerImpl;
import com.gleb.web.request.RequestParserImpl;
import com.gleb.web.response.ResponseManagerImpl;
import com.gleb.web.server.WebServer;
import com.gleb.web.server.WebServerImpl;

import java.io.IOException;

public class WebServerApplication {
    public static void main(String[] args) throws IOException {
        WebServer webServer = new WebServerImpl(8080, new RequestParserImpl(),
                new ResponseManagerImpl(), new FileManagerImpl());

        webServer.start();
    }
}
