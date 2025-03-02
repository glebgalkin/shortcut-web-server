package com.gleb.web.network.response;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class HttpResponseBuilder {

    private HttpResponse httpResponse = new HttpResponse();

    public HttpResponse build(Status status, File file) throws IOException {
        return httpResponse;
    }

    private HttpResponseBuilder withStatus(int code) {
        httpResponse.setStatus(code);
        return this;
    }

    private HttpResponseBuilder withHeader(String header, String value) {
        httpResponse.getHeaders().put(header, value);
        return this;
    }

    private static Map<String, String> getHeaders(File file) throws IOException {
        Map<String, String> headers = new HashMap<>();

        headers.put(Header.CONTENT_TYPE.getName(), getContentType(file));
        headers.put(Header.CACHE_CONTROL.getName(), Header.CacheControl.NO_CACHE.getName());
        headers.put(Header.CONTENT_LENGTH.getName(), String.valueOf(file.length()));
        return headers;
    }


    // TODO may be to file service
    private static String getContentType(File file) throws IOException {
        String contentType = Files.probeContentType(file.toPath());
        if(contentType.contains("html")) return Header.ContentType.TEXT_HTML.getName();
        return contentType;
    }
}
