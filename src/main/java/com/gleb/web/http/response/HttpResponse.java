package com.gleb.web.http.response;

import lombok.Data;

import java.io.InputStream;
import java.util.Map;

@Data
public class HttpResponse {
    private String status;
    private Map<String, String> headers;
    private InputStream body;
}
