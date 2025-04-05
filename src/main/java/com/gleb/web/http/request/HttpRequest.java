package com.gleb.web.http.request;

import lombok.Builder;
import lombok.Data;

import java.io.File;
import java.util.Map;

@Data
@Builder
public class HttpRequest {
    private HttpMethod method;
    private String path;
    private Map<String, String> headers;
    private File body;
}
