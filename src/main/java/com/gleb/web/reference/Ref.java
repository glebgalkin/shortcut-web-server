package com.gleb.web.reference;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Ref {

    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started at port " + PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New connection!");

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream(), StandardCharsets.UTF_8));

                PrintWriter writer = new PrintWriter(socket.getOutputStream(),
                        false, StandardCharsets.UTF_8);

                while (!reader.ready()) ; // TODO timeout is needed here

                while (reader.ready()) {
                    System.out.println(reader.readLine());
                }

                /*
                TODO 1. Распарсить запрос и выделить из него имя запрашиваемого файла.
                TODO      найти этот файл в заданной папке файловой системы и вернуть его содержимое
                TODO      в качестве ответа на запрос. Если файл не найде, то вернуть ошибку 404
                TODO 2. Подумать над архитектурой сервера и разделить код по нескольким классам, чтобы каждый из классов
                TODO      отвечал за какую-то минимальную функциональность
                 */
                writer.print("HTTP/1.1 200 OK");
                writer.println("Content-Type: text/html;UTF-8");
                writer.println();
                writer.println("<h1>Hello</h1>");
                writer.println("<p>It works!</p>");
                writer.flush();
                socket.close();
            }
        }
    }
}
