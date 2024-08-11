import classes.order.BookingBody;
import classes.order.OrderLog;
import io.restassured.RestAssured;
import org.junit.Test;
import specs.Spec;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static foldedCreator.Folder.createFolderIfNotExists;
import static io.restassured.http.ContentType.JSON;

public class test extends Spec {
    @Test
    public void getDate() {
        String azaza = "240602-145771";
        long start = System.currentTimeMillis();
        System.out.println(Arrays.toString(getOrderInfo(azaza)));
        System.out.println("Rest Assured time: " + (System.currentTimeMillis() - start));
    }

    public Map<String, List<OrderLog>> separateByShop(List<OrderLog> logs) {
        Map<String, List<OrderLog>> logsByShop = new HashMap<>();

        for (OrderLog log : logs) {
            logsByShop
                    .computeIfAbsent(log.getId_shop(), k -> new ArrayList<>())
                    .add(log);
        }

        return logsByShop;
    }

    private OrderLog[] getOrderInfo(String order){
        //initiate a path
        final String URL_BASE = "https://booking.geoapteka.com.ua/get-logs";
        //initiate a constructor
        InstallSpec(reqSpec(URL_BASE,""), respSpec());
        //initiate a response body
        BookingBody bd = new BookingBody(order);
        //request to booking
        return RestAssured
                .given()
                .header("Connection","keep-alive")
                .header("Accept","*/*")
                .header("Accept-Encoding","gzip, deflate, br")
                .contentType(JSON)
                .body(bd)
                .when()
                .post()
                .then()
                .extract().response().as(classes.order.OrderLog[].class);
    }

    private String getDateNow() {
        DateTimeFormatter formatterOnlyDate = DateTimeFormatter.ofPattern("yyyy.MM.dd");// HH-mm
        return LocalDateTime.now().format(formatterOnlyDate);
    }
    private String folderPreparation() {
        final String BASE_FOLDER = "report";
         String dateFolder = "";
         String timeFolder = "";
         final String BACK_UP_FOLDER = "backUp";
        String result = "";
        try {
            String base = getDesktopPath() + File.separator + BASE_FOLDER;
            createFolderIfNotExists(base);
            createFolderIfNotExists(base + File.separator + BACK_UP_FOLDER);
            createFolderIfNotExists(base + File.separator + getDateNow());
            result = base + File.separator + getDateNow() + File.separator + LocalDateTime.now().getHour();
            createFolderIfNotExists(result);

        } catch (Exception e) {
            //logger.error("Bad deals with a folder: " + e.getMessage());
        }
        return result;
    }

    public static String getDesktopPath() {
        String osName = System.getProperty("os.name").toLowerCase();
        String userHome = System.getProperty("user.home");

        if (osName.contains("win")) {
            return userHome + "\\OneDrive - Proximaresearch International LLC\\Робочий стіл";
        } else if (osName.contains("mac")) {
            return userHome + "/Desktop";
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
            return userHome + "/Desktop";
        } else {
            // Handle other operating systems if needed
            return null;
        }
    }
}
