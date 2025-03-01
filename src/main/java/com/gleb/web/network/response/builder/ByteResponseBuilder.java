package com.gleb.web.network.response.builder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

public class ByteResponseBuilder {
    public static byte[] build(HttpResponse response) throws IOException {
        String statusAndHeaders = getStatusAndHeadersAsString(response);
        byte[] headerBytes = statusAndHeaders.getBytes();
        byte[] fileBytes = response.getBody() != null ? getFileBytes(response.getBody()) : new byte[0];

        byte[] fullResponse = new byte[headerBytes.length + fileBytes.length];
        System.arraycopy(headerBytes, 0, fullResponse, 0, headerBytes.length);
        System.arraycopy(fileBytes, 0, fullResponse, headerBytes.length, fileBytes.length);
        return fullResponse;
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

    private static byte[] getFileBytes(File file) throws IOException {
        return Files.readAllBytes(file.toPath());
    }
}
