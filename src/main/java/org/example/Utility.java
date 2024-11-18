package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utility {
    public static String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
