package com.gleb.web.server.client;

import com.gleb.web.file.FileServiceImpl;
import com.gleb.web.http.HttpHandlerImpl;
import com.gleb.web.http.request.RequestFactoryImpl;
import com.gleb.web.http.response.HttpResponseFactoryImpl;
import com.gleb.web.server.client.handler.ClientHandler;
import com.gleb.web.server.client.handler.ClientHandlerImpl;

import java.net.Socket;

public class ClientHandlerFactory {
    public static ClientHandler create(Socket socket) {
        return new ClientHandlerImpl(socket, new FileServiceImpl(),
                new HttpHandlerImpl(socket, new RequestFactoryImpl()), new HttpResponseFactoryImpl());
    }
}
