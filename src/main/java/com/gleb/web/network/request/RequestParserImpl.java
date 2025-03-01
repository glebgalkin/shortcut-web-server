package com.gleb.web.network.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class RequestParserImpl implements RequestParser {
    private static final Logger logger = Logger.getLogger(RequestParserImpl.class.getName());

    // TODO it's going to be better to return here some domain class instance
    //  with all information about request (file name, protocol version,
    //  headers, stream to read request body if needed)
    @Override
    public String parseFilePath(Socket socket) throws IOException {
        logger.info("New connection: " + socket.toString());

        // TODO probably it's better to delegate all sockets interaction to some NetworkService
        //  request parser and response manager shall be responsible only for text processing but not
        //  for the network IO
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream(), StandardCharsets.UTF_8));

        String requestLine = reader.readLine(); // TODO timeout
        return parseFilePath(requestLine);
    }

    private String parseFilePath(String requestLine) {
        String[] items = requestLine.split(" "); // TODO handle error in case request is corrupted
        return items[1];
    }
}
