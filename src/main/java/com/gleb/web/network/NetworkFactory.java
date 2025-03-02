package com.gleb.web.network;

import java.net.Socket;

public class NetworkFactory {
    public static NetworkService getNetworkService(Socket socket){
        return new NetworkServiceImpl(socket);
    }
}
