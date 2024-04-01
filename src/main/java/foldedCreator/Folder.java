package foldedCreator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
public class Folder {
    private static final Logger logger = LoggerFactory.getLogger(Folder.class);
    public static void createFolderIfNotExists(String folderPath) throws IOException {
        Path path = Paths.get(folderPath);
        try {
            // Check if the folder exists
            if (!Files.exists(path)) {
                // Create the folder and its parent directories if they don't exist
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            logger.error("Error creating folder: " + e.getMessage());
        }

    }
}
