package com.gleb.web.server;

import com.gleb.web.concurrency.NamedThreadFactory;
import com.gleb.web.config.ConfigLoader;

import java.util.concurrent.Executors;

public final class WebServerFactory {

    public static WebServer create() {
        return new WebServerImpl(getPort(), Executors.newFixedThreadPool(getThreads(),
                new NamedThreadFactory()));
    }

    private static int getPort() {
        return Integer.parseInt(ConfigLoader.get("server.port"));
    }

    private static int getThreads() {
        return Integer.parseInt(ConfigLoader.get("server.threads"));
    }
}
