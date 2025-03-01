package com.gleb.web.server;

import com.gleb.web.config.ConfigLoader;

public final class WebServerFactory {

    public static WebServer create() {
        return new WebServerImpl(getPort());
    }

    private static int getPort(){
        return Integer.parseInt(ConfigLoader.get("server.port"));
    }
}
