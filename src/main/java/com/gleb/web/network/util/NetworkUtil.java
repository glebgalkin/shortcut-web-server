package com.gleb.web.network.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class NetworkUtil {
    public static String getRawRequest(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            sb.append(line);
            sb.append("\n");
        }
        return sb.toString();
    }

    public static InetAddress parseIpString(String ip) throws UnknownHostException {
        String[] parts = ip.split("\\.");
        byte[] bytes = new byte[4];

        for (int i = 0; i < 4; i++) {
            bytes[i] = (byte) Integer.parseInt(parts[i]);
        }
        return InetAddress.getByAddress(bytes);
    }
}
