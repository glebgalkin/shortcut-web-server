package com.gleb.web.http.response;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class HttpResponseFactoryImpl implements HttpResponseFactory{

    public HttpResponse build(Status status, File file) throws IOException {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setStatus(status.getName());
        httpResponse.setHeaders(getHeaders(file));
        httpResponse.setBody(new FileInputStream(file));
        return httpResponse;
    }

    private Map<String, String> getHeaders(File file) throws IOException {
        Map<String, String> headers = new HashMap<>();

        headers.put(Header.CONTENT_TYPE.getName(), getContentType(file));
        headers.put(Header.CACHE_CONTROL.getName(), Header.CacheControl.NO_CACHE.getName());
        headers.put(Header.CONTENT_LENGTH.getName(), String.valueOf(file.length()));
        return headers;
    }


    private String getContentType(File file) throws IOException {
        String contentType = Files.probeContentType(file.toPath());
        if(contentType.contains("html")) return Header.ContentType.TEXT_HTML.getName();
        return contentType;
    }
}
