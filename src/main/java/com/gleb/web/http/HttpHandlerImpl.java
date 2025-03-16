package com.gleb.web.http;

import com.gleb.web.http.request.HttpRequest;
import com.gleb.web.http.request.RequestFactory;
import com.gleb.web.http.response.HttpResponse;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpHandlerImpl implements HttpHandler {
    private final Socket socket;
    private final RequestFactory requestFactory;

    public HttpHandlerImpl(Socket socket, RequestFactory requestFactory) {
        this.socket = socket;
        this.requestFactory = requestFactory;
    }

    @Override
    public HttpRequest getRequest() throws IOException {
        String rawData = getRawRequest(socket.getInputStream());
        return requestFactory.build(rawData);
    }

    private String getRawRequest(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            sb.append(line);
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public void sendResponse(HttpResponse httpResponse) throws IOException {
        OutputStream outputStream = socket.getOutputStream();

        byte[] statusAndHeaders = getStatusAndHeadersAsBytes(httpResponse);
        outputStream.write(statusAndHeaders);
        writeBody(httpResponse.getBody(), outputStream);

        outputStream.close();
        socket.close();
    }

    private byte[] getStatusAndHeadersAsBytes(HttpResponse response){
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
        return sb.toString().getBytes();
    }

    private void writeBody(InputStream inputStream, OutputStream outputStream) throws IOException {
        if (inputStream == null) return;
        inputStream.transferTo(outputStream);
        inputStream.close();
    }
}
