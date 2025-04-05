package com.gleb.web.http;

import com.gleb.web.http.request.HttpRequest;
import com.gleb.web.http.request.RequestParser;
import com.gleb.web.http.response.HttpResponse;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class HttpHandlerImpl implements HttpHandler {
    private final Socket socket;
    private final RequestParser requestParser;

    public HttpHandlerImpl(Socket socket, RequestParser requestParser) {
        this.socket = socket;
        this.requestParser = requestParser;
    }

    @Override
    public HttpRequest getRequest() throws IOException {
        return requestParser.parse(socket.getInputStream());
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
