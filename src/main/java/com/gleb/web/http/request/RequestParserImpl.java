package com.gleb.web.http.request;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RequestParserImpl implements RequestParser{
    private int contentLength = 0;
    private static final String CONTENT_LENGTH_HEADER = "Content-Length";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";

    @Override
    public HttpRequest parse(InputStream socketStream) throws IOException {
        ByteArrayOutputStream headerStream = new ByteArrayOutputStream();

        int prev = -1, curr;
        while ((curr = socketStream.read()) != -1) {
            headerStream.write(curr);
            if (prev == '\r' && curr == '\n') {
                byte[] data = headerStream.toByteArray();
                int len = data.length;
                if (len >= 4 &&
                        data[len - 4] == '\r' &&
                        data[len - 3] == '\n' &&
                        data[len - 2] == '\r' &&
                        data[len - 1] == '\n') {
                    break; // End of headers
                }
            }
            prev = curr;
        }

        String headersString = headerStream.toString(StandardCharsets.UTF_8);
        String[] lines = headersString.split("\r\n");

        String requestLine = lines[0];
        String[] requestLineParts = requestLine.split(" ");
        HttpMethod method = HttpMethod.valueOf(requestLineParts[0]);
        String path = requestLineParts[1];

        Map<String, String> headers = new HashMap<>();
        int contentLength = 0;

        for (int i = 1; i < lines.length; i++) {
            String[] entry = lines[i].split(": ", 2);
            if (entry.length == 2) {
                headers.put(entry[0], entry[1]);
                if (entry[0].equalsIgnoreCase("Content-Length")) {
                    contentLength = Integer.parseInt(entry[1]);
                }
            }
        }

        File destination = null;
        if (contentLength > 0) {
            String contentType = headers.get("Content-Type");
            String extensionType = getExtensionFromContentType(contentType);
            String fileName = "upload_" + System.currentTimeMillis() + "." + extensionType;
            destination = new File("public/files/" + fileName);

            try (FileOutputStream fileOut = new FileOutputStream(destination)) {
                byte[] buffer = new byte[8192];
                int totalRead = 0;

                while (totalRead < contentLength) {
                    int bytesToRead = Math.min(buffer.length, contentLength - totalRead);
                    int bytesRead = socketStream.read(buffer, 0, bytesToRead);
                    if (bytesRead == -1) break;
                    fileOut.write(buffer, 0, bytesRead);
                    totalRead += bytesRead;
                }
            }
        }

        return HttpRequest.builder()
                .method(method)
                .path(path)
                .headers(headers)
                .body(destination)
                .build();
    }


    private HttpMethod getHttpMethod(String[] requestLineParts){
        return HttpMethod.valueOf(requestLineParts[0]);
    }

    private Map<String, String> getHeaders(BufferedReader reader) throws IOException {
        Map<String, String> headers = new HashMap<>();
        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()){
            String[] entry = line.split(": ");
            String header = entry[0];
            String value = entry[1];
            if(header.equals(CONTENT_LENGTH_HEADER)) contentLength = Integer.parseInt(value);
            headers.put(header, value);
        }
        return headers;
    }

    private String getExtensionFromContentType(String contentType) {
        if (contentType == null) return "bin";
        return switch (contentType) {
            case "image/jpeg" -> "jpg";
            case "image/png"  -> "png";
            case "image/gif"  -> "gif";
            case "text/html"  -> "html";
            case "application/json" -> "json";
            default -> "bin";
        };
    }

}
