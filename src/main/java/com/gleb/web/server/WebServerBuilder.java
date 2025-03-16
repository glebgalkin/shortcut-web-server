package com.gleb.web.server;

import com.gleb.web.concurrency.NamedThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServerBuilder {
    private int port = 8080; // Default port
    private String bindAddress = "127.0.0.1";
    private int threadPoolSize = 10;
    private ExecutorService executorService;

    public WebServerBuilder setPort(int port) {
        this.port = port;
        return this;
    }

    public WebServerBuilder setBindAddress(String bindAddress) {
        this.bindAddress = bindAddress;
        return this;
    }

    public WebServerBuilder setThreadPoolSize(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
        return this;
    }

    public WebServerBuilder setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
        return this;
    }

    public WebServer build() {
        if (executorService == null) {
            executorService = Executors.newFixedThreadPool(threadPoolSize, new NamedThreadFactory());
        }
        return new WebServerImpl(port, bindAddress, executorService);
    }
}
