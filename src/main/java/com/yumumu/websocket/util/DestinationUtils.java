package com.yumumu.websocket.util;

public class DestinationUtils {

    public static String generateDestination(String dest) {
        return  "/topic/v1/ws/" + dest + "/message";
    }

}
