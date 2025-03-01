package com.gleb.web.server.client;

import java.net.Socket;

public class ClientHandlerFactory {
    public static ClientHandler create(Socket socket){
        return new ClientHandlerImpl(socket);
    }
}
