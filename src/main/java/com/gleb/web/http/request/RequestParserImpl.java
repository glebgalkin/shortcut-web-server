package com.gleb.web.http.request;

import com.gleb.web.file.FileService;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RequestParserImpl implements RequestParser {
    private static final String CONTENT_LENGTH_HEADER = "Content-Length";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private int contentLength = 0;
    private final FileService fileService;

    public RequestParserImpl(FileService fileService){
        this.fileService = fileService;
    }

    @Override
    public HttpRequest parse(InputStream socketStream) throws IOException {

        String rawHeaders = parseRawHeaders(socketStream);
        String[] lines = rawHeaders.split("\r\n");

        String requestLine = lines[0];
        String[] requestLineParts = requestLine.split(" ");
        HttpMethod method = HttpMethod.valueOf(requestLineParts[0]);
        String path = requestLineParts[1];

        Map<String, String> headers = getHeaders(lines);
        File requestBodyFile = parseBody(headers, socketStream);

        return HttpRequest.builder()
                .method(method)
                .path(path)
                .headers(headers)
                .body(requestBodyFile)
                .build();
    }

    private String parseRawHeaders(InputStream socketStream) throws IOException {
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
        return headerStream.toString(StandardCharsets.UTF_8);
    }

    private File parseBody(Map<String, String> headers, InputStream socketStream) throws IOException {
        if (contentLength == 0) return null;

        String contentType = headers.get(CONTENT_TYPE_HEADER);
        String extensionType = getExtensionFromContentType(contentType);

        String fileName = "upload_" + System.currentTimeMillis() + "." + extensionType;
        return fileService.buildFileFromStream(fileName, socketStream, contentLength);
    }

    private Map<String, String> getHeaders(String[] lines) {
        Map<String, String> headers = new HashMap<>();
        for (int i = 1; i < lines.length; i++) {
            String[] entry = lines[i].split(": ", 2);
            if (entry.length == 2) {
                headers.put(entry[0], entry[1]);
                if (entry[0].equalsIgnoreCase(CONTENT_LENGTH_HEADER)) {
                    contentLength = Integer.parseInt(entry[1]);
                }
            }
        }
        return headers;
    }

    private String getExtensionFromContentType(String contentType) {
        if (contentType == null) return "bin";
        return switch (contentType) {
            case "image/jpeg" -> "jpg";
            case "image/png" -> "png";
            case "image/gif" -> "gif";
            case "text/html" -> "html";
            case "application/json" -> "json";
            default -> "bin";
        };
    }
}
