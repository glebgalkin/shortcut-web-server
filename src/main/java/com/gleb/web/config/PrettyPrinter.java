package com.gleb.web.config;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Slf4j
public class PrettyPrinter {

    // TODO move to resource file
    public static void logBanner() {
        String banner = loadBanner();
        String coloredBanner = "\u001B[31m" + banner + "\u001B[0m";
        System.out.println(coloredBanner);
    }

    private static String loadBanner() {
        StringBuilder banner = new StringBuilder();
        try (InputStream is = PrettyPrinter.class.getClassLoader().getResourceAsStream("banner.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

            String line;
            // TODO reader.lines().collect(Collectors.joining("\n"));
            while ((line = reader.readLine()) != null) {
                banner.append(line).append("\n");
            }
        } catch (IOException | NullPointerException e) {
            log.error("Failed to load banner.txt", e);
            return "Default App Banner";
        }
        return banner.toString();
    }


    public static String getAppName() {
        return String.format("%s\u001B[32m%s \u001B[0m%s", getRandomRocketEmoji(),
                ConfigLoader.get("application.name"), getRandomRocketEmoji());
    }

    public static String getRandomRocketEmoji() {
        String[] rockets = {"🚀", "🛸", "🔥", "💨", "✨", "🌟"};
        return rockets[new Random().nextInt(rockets.length)];
    }

    public static void logSuccessfulStart(String bindAddress, int port) throws UnknownHostException {
        log.info("{} started at: http://localhost:{}", getAppName(), port);
        String localIp = InetAddress.getLocalHost().getHostAddress();
        log.info("📡 Network:    http://{}:{}", localIp, port);
        log.info("🔗 Bound to:   http://{}:{}", bindAddress, port);
    }
}
