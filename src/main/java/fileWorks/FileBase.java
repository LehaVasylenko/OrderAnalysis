package fileWorks;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class FileBase {
    protected static String PROJECT_ROOT = System.getProperty("user.dir");
    protected static String SOURCE_DIR = "orders";
    protected static String IGNORED_ORDERS = "ignored";
    protected static String VALID_ORDERS = "valid";
    protected static String BACKUP_IGNORED_ORDERS = "backup_ignored";
    protected static String BACKUP_VALID_ORDERS = "backup_valid";
    protected static ObjectMapper objectMapper = new ObjectMapper();
    protected static Path filePath;
    protected static Path init(String file) {
        Path sourceDirectory = Paths.get(PROJECT_ROOT, SOURCE_DIR);
        return sourceDirectory.resolve(file + ".json");
    }
}
