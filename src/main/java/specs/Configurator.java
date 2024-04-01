package specs;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Configurator {
    private static final Logger logger = LoggerFactory.getLogger(Configurator.class);
    private static final String LOG4J_PROPERTIES = "src/main/resources/log4j.properties";
    private static final String LOG_PATH = "logs";

    protected static void init() {
        // Set the current date in the Log4j properties
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(new Date());
        System.setProperty("currentDate", currentDate);

        initializeLog4jProperties();
        initializeLogPath();
    }

    private static void initializeLogPath() {
        Path path = Paths.get(LOG_PATH);
        try {
            // Check if the folder exists
            if (!Files.exists(path)) {
                // Create the folder and its parent directories if they don't exist
                Files.createDirectories(path);
                logger.info("Folder " + LOG_PATH + " created successfully.");
            }
        } catch (IOException e) {
            logger.error("Error creating folder: " + e.getMessage());
        }
    }

    private static void initializeLog4jProperties() {
        // Create a Path object from the file path string
        Path log4jPropertiesFile = Paths.get(LOG4J_PROPERTIES);

        // Check if the log4j.properties file exists
        if (!Files.exists(log4jPropertiesFile)) {
            // Log4j properties file doesn't exist, create it with custom properties
            try (BufferedWriter writer = Files.newBufferedWriter(log4jPropertiesFile)) {
                // Write custom properties to the file
                writer.write("# Root logger option\n");
                writer.write("log4j.rootLogger=INFO, file, stdout\n\n");

                writer.write("# Direct log messages to a log file\n");
                writer.write("log4j.appender.file=org.apache.log4j.RollingFileAppender\n");
                writer.write("log4j.appender.file.File=logs/${currentDate}_priceLog.log\n");
                writer.write("log4j.appender.file.MaxFileSize=10MB\n");
                writer.write("log4j.appender.file.MaxBackupIndex=10\n");
                writer.write("log4j.appender.file.layout=org.apache.log4j.PatternLayout\n");
                writer.write("log4j.appender.file.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n\n\n");

                writer.write("# Direct log messages to stdout\n");
                writer.write("log4j.appender.stdout=org.apache.log4j.ConsoleAppender\n");
                writer.write("log4j.appender.stdout.Target=System.out\n");
                writer.write("log4j.appender.stdout.layout=org.apache.log4j.PatternLayout\n");
                writer.write("log4j.appender.stdout.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n\n");

                logger.info("log4j.properties file created successfully.");
            } catch (IOException e) {
                logger.error("Error creating log4j.properties file: " + e.getMessage());
            }
        } else {
            logger.info("log4j.properties file already exists.");
        }
        PropertyConfigurator.configure(LOG4J_PROPERTIES);
    }

    protected static void workTime (long startTime, long endTime) {
        // Calculate the total execution time
        long totalTime = endTime - startTime;
        // Calculate minutes, seconds, and remaining milliseconds
        long minutes = (totalTime / 1000) / 60;
        long seconds = (totalTime / 1000) % 60;
        long milliseconds = totalTime % 1000;
        // Print the result
        logger.info("Total Execution Time: " + minutes + " min, " + seconds + " sec, " + milliseconds + " ml sec");
    }
}
