package com.gleb.web.network;

import com.gleb.web.network.request.HttpRequest;
import com.gleb.web.network.request.RequestMapper;
import com.gleb.web.network.response.builder.HttpResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class NetworkService {
    private Socket socket;

    public NetworkService(Socket socket){
        this.socket = socket;
    }

    public HttpRequest getRequest(){
        try {
            String rawData = NetworkUtil.getRawRequest(socket.getInputStream());
            return RequestMapper.buildRequest(rawData);
        } catch (IOException e) {
            // TODO: Handle exception
        }
        return null;
    }

    public void sendResponse(byte[] response) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(response);
        outputStream.flush();
        outputStream.close(); // 🔥 Close stream after sending response
        socket.close(); // 🔥
    }
}
