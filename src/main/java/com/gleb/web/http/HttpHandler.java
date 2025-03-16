package com.gleb.web.http;

import com.gleb.web.http.request.HttpRequest;
import com.gleb.web.http.response.HttpResponse;

import java.io.IOException;

public interface HttpHandler {
    HttpRequest getRequest() throws IOException;

    void sendResponse(HttpResponse httpResponse) throws IOException;
}
