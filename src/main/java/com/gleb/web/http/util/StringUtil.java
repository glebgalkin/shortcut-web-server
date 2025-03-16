package com.gleb.web.http.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public final class StringUtil {

    public static InetAddress parseIpString(String ip) throws UnknownHostException {
        String[] parts = ip.split("\\.");
        byte[] bytes = new byte[4];

        for (int i = 0; i < 4; i++) {
            bytes[i] = (byte) Integer.parseInt(parts[i]);
        }
        return InetAddress.getByAddress(bytes);
    }
}
