package com.gleb.web.network.request;

import lombok.Data;

import java.io.File;
import java.util.Map;

@Data
public class HttpRequest {
    private RequestType requestType;
    private String path;
    private Map<String, String> headers;
    private File body;
}
