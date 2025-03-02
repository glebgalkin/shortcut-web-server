package com.gleb.web.network;

import com.gleb.web.network.request.HttpRequest;
import com.gleb.web.network.request.RequestMapper;
import com.gleb.web.network.response.ByteResponseConverter;
import com.gleb.web.network.response.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class NetworkServiceImpl implements NetworkService {
    private Socket socket;

    public NetworkServiceImpl(Socket socket) {
        this.socket = socket;
    }

    @Override
    public HttpRequest getRequest() throws IOException {
        String rawData = NetworkUtil.getRawRequest(socket.getInputStream());
        return RequestMapper.buildRequest(rawData);
    }

    @Override
    public void sendResponse(HttpResponse httpResponse) throws IOException {
        OutputStream outputStream = socket.getOutputStream();

        byte[] statusAndHeaders = ByteResponseConverter.getStatusAndHeadersAsBytes(httpResponse);
        outputStream.write(statusAndHeaders);
        writeBody(httpResponse.getBody(), outputStream);

        outputStream.close();
        socket.close();
    }

    private void writeBody(InputStream inputStream, OutputStream outputStream) throws IOException {
        if (inputStream == null) return;

        byte[] buffer = new byte[8192];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        inputStream.close();
    }
}
