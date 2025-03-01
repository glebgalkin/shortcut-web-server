package com.gleb.web.network.request;

import java.util.HashMap;
import java.util.Map;

public class RequestMapper {
    public static HttpRequest buildRequest(String rawData){
        HttpRequest httpRequest = new HttpRequest();

        String[] lines = rawData.split("\n");
        String[] title = getTitle(lines);

        httpRequest.setRequestType(getRequestType(title));
        httpRequest.setPath(getPath(title));
        httpRequest.setHeaders(getHeaders(lines));
        return httpRequest;
    }

    private static String[] getTitle(String[] lines){
        return lines[0].split(" ");
    }

    private static RequestType getRequestType(String[] title){
        return RequestType.valueOf(title[0]);
    }

    private static String getPath(String[] title){
        return title[1];
    }

    private static Map<String, String> getHeaders(String[] rawHeaders){
        Map<String, String> headers = new HashMap<>();
        for(String rawLine : rawHeaders){
            String[] headerLine = rawLine.split(" ");
            headers.put(headerLine[0].trim(), headerLine[1].trim());
        }
        return headers;
    }
}
