package audit;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggingService {
    private static final String LOG_FILE_PATH = "logs.csv";

    public static void logAction(String action, String username) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String logEntry = String.format("%s,%s,%s\n", action, timestamp, username);

        try (FileWriter writer = new FileWriter(LOG_FILE_PATH, true)) {
            writer.write(logEntry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}