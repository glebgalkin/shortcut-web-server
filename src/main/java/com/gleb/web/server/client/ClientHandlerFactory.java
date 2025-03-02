package com.gleb.web.server.client;

import com.gleb.web.server.client.handler.ClientHandler;
import com.gleb.web.server.client.handler.ClientHandlerImpl;

import java.net.Socket;

public class ClientHandlerFactory {
    public static ClientHandler create(Socket socket){
        return new ClientHandlerImpl(socket);
    }
}
