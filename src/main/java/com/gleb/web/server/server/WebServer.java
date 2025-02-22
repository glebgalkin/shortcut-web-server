package com.gleb.web.server.server;

import java.io.IOException;

public interface WebServer {
    void start() throws IOException;

    void terminate();
}
