package com.gleb.web.network.response.util;

import lombok.Data;

import java.io.File;
import java.util.Map;

@Data
public class HttpResponse {
    private String status;
    private Map<String, String> headers;
    private File body;
}
