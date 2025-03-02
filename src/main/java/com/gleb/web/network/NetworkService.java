package com.gleb.web.network;

import com.gleb.web.network.request.HttpRequest;
import com.gleb.web.network.response.util.HttpResponse;

import java.io.IOException;

public interface NetworkService {
    HttpRequest getRequest() throws IOException;

    void sendResponse(HttpResponse httpResponse) throws IOException;
}
