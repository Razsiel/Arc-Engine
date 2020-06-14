package nl.arkenbout.geoffrey.angel.engine.util;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ResourceUtils {
    public static String load(String fileName) throws Exception {
        String result;

        try (InputStream in = ResourceUtils.class.getResourceAsStream(fileName)) {
            try (Scanner scanner = new Scanner(in, StandardCharsets.UTF_8)) {
                result = scanner.useDelimiter("\\A").next();
            }
        }
        return result;
    }
}
