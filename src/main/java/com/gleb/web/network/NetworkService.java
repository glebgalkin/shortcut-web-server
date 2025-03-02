package com.gleb.web.network;

import com.gleb.web.network.request.HttpRequest;

import java.io.IOException;

public interface NetworkService {
    HttpRequest getRequest() throws IOException;

    void sendResponse(byte[] response) throws IOException;
}
