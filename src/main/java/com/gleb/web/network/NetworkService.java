package com.gleb.web.network;

import com.gleb.web.network.request.HttpRequest;
import com.gleb.web.network.request.RequestMapper;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class NetworkService {
    private Socket socket;

    public NetworkService(Socket socket) {
        this.socket = socket;
    }

    public HttpRequest getRequest() throws IOException {
        String rawData = NetworkUtil.getRawRequest(socket.getInputStream());
        return RequestMapper.buildRequest(rawData);
    }

    public void sendResponse(byte[] response) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(response);
        outputStream.flush();
        outputStream.close();
        socket.close();
    }
}
