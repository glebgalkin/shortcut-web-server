package com.gleb.web.config;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

@Slf4j
public class PrettyPrinter {

    // TODO move to resource file
    public static void logBanner() {
        String banner = "\n" +
                "\u001B[31m _____ _                _             _     _    _      _       _____                          \n" +
                "/  ___| |              | |           | |   | |  | |    | |     /  ___|                         \n" +
                "\\ `--.| |__   ___  _ __| |_ ___ _   _| |_  | |  | | ___| |__   \\ `--.  ___ _ ____   _____ _ __ \n" +
                " `--. | '_ \\ / _ \\| '__| __/ __| | | | __| | |/\\| |/ _ | '_ \\   `--. \\/ _ | '__\\ \\ / / _ | '__|\n" +
                "/\\__/ | | | | (_) | |  | || (__| |_| | |_  \\  /\\  |  __| |_) | /\\__/ |  __| |   \\ V |  __| |   \n" +
                "\\____/|_| |_|\\___/|_|   \\__\\___|\\__,_|\\__|  \\/  \\/ \\___|_.__/  \\____/ \\___|_|    \\_/ \\___|_|   \n" +
                "                                                                                               \n" +
                "                                                                                               \n\u001B[0m"; // Reset color
        System.out.println(banner);
    }


    public static String getAppName() {
        return String.format("%s\u001B[32m%s \u001B[0m%s", getRandomRocketEmoji(),
                ConfigLoader.get("application.name"), getRandomRocketEmoji());
    }

    public static String getRandomRocketEmoji() {
        String[] rockets = {"🚀", "🛸", "🔥", "💨", "✨", "🌟"};
        return rockets[new Random().nextInt(rockets.length)];
    }

    public static  void logSuccessfulStart(String bindAddress, int port) throws UnknownHostException {
        log.info("{} started at: http://localhost:{}", getAppName(), port);
        String localIp = InetAddress.getLocalHost().getHostAddress();
        log.info("📡 Network:    http://{}:{}", localIp, port);
        log.info("🔗 Bound to:   http://{}:{}", bindAddress, port);
    }
}
