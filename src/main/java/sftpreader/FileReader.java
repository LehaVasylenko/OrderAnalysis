package sftpreader;

import sftpreader.shopPrice.PriceItem;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class FileReader extends BaseSFTP{
    private static final Logger logger = LoggerFactory.getLogger(FileReader.class);

    public static ArrayList<PriceItem> readJsonArrayFromFile(String file) throws IOException {
        Path sourceDirectory = Paths.get(PROJECT_ROOT, SOURCE_DIR);
        Path filePath = sourceDirectory.resolve(file);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(filePath.toFile());

        ArrayList<PriceItem> priceItems = new ArrayList<>();

        if (rootNode.isArray()) {
            for (JsonNode jsonNode : rootNode) {
                PriceItem priceItem = objectMapper.treeToValue(jsonNode, PriceItem.class);
                priceItems.add(priceItem);
            }
        }

        return priceItems;
    }
}

