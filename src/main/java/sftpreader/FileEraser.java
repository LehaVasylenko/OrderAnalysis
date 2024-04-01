package sftpreader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
public class FileEraser extends BaseSFTP{
    private static final Logger logger = LoggerFactory.getLogger(FileEraser.class);

        public FileEraser(String fileName) {
            // Specify the path of the file to be deleted
            String sourcePath = SOURCE_DIR + File.separator + fileName;
            String archievePath = PRICES_DIR + File.separator + fileName + ".gz";

            try {
                // Use Files.delete() to delete the file
                Files.delete(Paths.get(archievePath));
                Files.delete(Paths.get(sourcePath));
                logger.info("File " + fileName + " deleted successfully.");
            } catch (IOException e) {
                logger.error("Error deleting the file: " + e.getMessage());
            }
        }
    }

