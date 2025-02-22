package com.gleb.web.server.server.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class InputReaderImpl implements InputReader {
    private static final Logger logger = Logger.getLogger(InputReaderImpl.class.getName());

    @Override
    public String parseFilePath(Socket socket) throws IOException {
        logger.info("New connection: " + socket.toString());

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream(), StandardCharsets.UTF_8));

        if(reader.ready()){
            String requestLine = reader.readLine();
            return parseFilePath(requestLine);
        }
        return "Kek";
    }

    private String parseFilePath(String requestLine){
        String[] items = requestLine.split(" ");
        return items[1];
    }
}
