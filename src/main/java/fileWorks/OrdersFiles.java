package fileWorks;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class OrdersFiles extends FileBase{
    private static final Logger logger = LoggerFactory.getLogger(OrdersFiles.class);

    public static void writeOrdersToFile(Collection<String> results, boolean valid, boolean ignored, boolean backUp) {

        if (!backUp) {
            if (valid) filePath = init(VALID_ORDERS);
            if (ignored) filePath = init(IGNORED_ORDERS);
        } else {
            if (valid) filePath = init(BACKUP_VALID_ORDERS);
            if (ignored) filePath = init(BACKUP_IGNORED_ORDERS);
        }

        // Check if the folder exists
        File directory = new File(SOURCE_DIR);

        if (!directory.exists()) {
            // If the folder doesn't exist, create it
            boolean created = directory.mkdirs();
        }

        // Create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        // Enable pretty-printing of JSON
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            // Write the array of objects to a JSON file
            objectMapper.writeValue(new File(String.valueOf(filePath)), results);
            logger.info("Array of " + results.size() + " objects written to " + filePath + " file successfully.");
        } catch (IOException e)
        {
            logger.error(e.getMessage());
        }
    }

    public static List<String> readOrdersFromFile(boolean valid, boolean ignored) {

        if (valid) filePath = init(VALID_ORDERS);
        if (ignored) filePath = init(IGNORED_ORDERS);

        List<String> orders = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            JsonNode rootNode = objectMapper.readTree(reader);

            if (rootNode.isArray()) {
                for (JsonNode jsonNode : rootNode) {
                    String priceItem = objectMapper.treeToValue(jsonNode, String.class);
                    orders.add(priceItem);
                }
            }

            logger.info(orders.size() + " elements successfully read from file " + filePath);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        //remember old state
        if (valid) writeOrdersToFile(orders, true, false,true);
        if (ignored) writeOrdersToFile(orders, false, true,true);

        return orders;
    }
}
