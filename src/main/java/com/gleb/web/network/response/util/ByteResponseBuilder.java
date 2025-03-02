package com.gleb.web.network.response.util;

import java.util.Map;

public class ByteResponseBuilder {

    public static byte[] getStatusAndHeadersAsBytes(HttpResponse response){
        String statusAndHeaders = getStatusAndHeadersAsString(response);
        return statusAndHeaders.getBytes();
    }

    private static String getStatusAndHeadersAsString(HttpResponse response){
        StringBuilder sb = new StringBuilder();
        sb.append(response.getStatus());
        sb.append("\n");
        for(Map.Entry<String, String> entry : response.getHeaders().entrySet()){
            sb.append(entry.getKey());
            sb.append(": ");
            sb.append(entry.getValue());
            sb.append("\n");
        }
        sb.append("\n");
        return sb.toString();
    }
}
