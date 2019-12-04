package nl.arkenbout.geoffrey.angel.engine.util;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Utils {
    public static String loadResource(String fileName) throws Exception {
        String result;

        try (InputStream in = Utils.class.getResourceAsStream(fileName)) {
            try (Scanner scanner = new Scanner(in, StandardCharsets.UTF_8)) {
                result = scanner.useDelimiter("\\A").next();
            }
        }
        return result;
    }
}
