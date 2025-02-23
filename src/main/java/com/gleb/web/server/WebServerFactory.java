package com.gleb.web.server;

import com.gleb.web.file.manager.FileManagerImpl;
import com.gleb.web.request.RequestParserImpl;
import com.gleb.web.response.ResponseManagerImpl;

public final class WebServerFactory {

    public static WebServer create(int port) {
        return new WebServerImpl(port, new RequestParserImpl(),
                new ResponseManagerImpl(), new FileManagerImpl());
    }
}
