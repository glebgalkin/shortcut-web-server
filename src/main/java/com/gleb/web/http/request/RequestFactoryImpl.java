package com.gleb.web.http.request;

import java.util.HashMap;
import java.util.Map;

public class RequestFactoryImpl implements RequestFactory{

    @Override
    public HttpRequest build(String rawData){
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
        for(int i = 1; i < rawHeaders.length; i++){
            String[] headerLine = rawHeaders[i].split(" ");
            headers.put(trimHeader(headerLine), headerLine[1].trim());
        }
        return headers;
    }

    private static String trimHeader(String[] headerLine){
        return headerLine[0].substring(0, headerLine[0].length()-1).trim();
    }
}
