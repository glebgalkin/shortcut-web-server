package com.gleb.web.server.server;

import com.gleb.web.server.server.file.parser.FileUtil;
import com.gleb.web.server.server.reader.InputReader;
import com.gleb.web.server.server.reader.InputReaderImpl;
import com.gleb.web.server.server.writer.Writer;
import com.gleb.web.server.server.writer.WriterImpl;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class WebServerImpl implements WebServer {

    private static final Logger logger = Logger.getLogger(WebServerImpl.class.getName());

    private final int PORT;
    private InputReader inputReader;
    private Writer outputWriter;


    public WebServerImpl() throws IOException {
        this.PORT = 8080;
        this.inputReader = new InputReaderImpl();
        this.outputWriter = new WriterImpl();
    }

    public WebServerImpl(int PORT){
        this.PORT = PORT;
        this.inputReader = new InputReaderImpl();
        this.outputWriter = new WriterImpl();
    }

    @Override
    public void start() throws IOException {
        try(ServerSocket serverSocket = new ServerSocket(this.PORT)){
            logger.info("Server started at port: " + PORT);

            while (true){
                Socket socket = serverSocket.accept();
                String requestedFileName = this.inputReader.parseFilePath(socket);
                File file = FileUtil.getFile(requestedFileName);
                outputWriter.sendFile(socket, file);
                socket.close();
            }
        }
    }

    @Override
    public void terminate() {

    }
}
