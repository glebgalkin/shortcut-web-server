package com.gleb.web.server.server.http.response;

import java.util.ArrayList;
import java.util.List;

public class HttpRequest {
    private Status status;
    private final List<String> headers;

    public HttpRequest(){
        this.headers = new ArrayList<>();
    }

    public void setStatus(Status status){
        this.status = status;
    }

    public void addHeader(String key, String value){
        String format = String.format("%s: %s", key, value);
        headers.add(format);
    }
}
