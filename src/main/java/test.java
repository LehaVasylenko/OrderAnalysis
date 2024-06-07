import classes.DrugsInfoMany;
import classes.Statistics;
import classes.order.BookingBody;
import classes.order.OrderList;
import classes.order.OrderLog;
import classes.shops.ShowShops;
import io.restassured.RestAssured;
import sftpreader.SftpManager;
import org.junit.Test;
import sftpreader.shopPrice.PriceItem;
import specs.Spec;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static foldedCreator.Folder.createFolderIfNotExists;
import static io.restassured.http.ContentType.JSON;

public class test extends Spec {
    @Test
    public void getDate() {
        String azaza = "240602-145771";
        OrderLog[] order = getOrderInfo(azaza);
        for (int i = 0; i < order.length; i++) {
            order[i].setShopInfo(new ShowShops("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", 1 ,1, "1", "1", "1", "1", "1", "1", "1", 1.0, 1.0,"1", "1", "1", "1"));
            for (int j = 0; j < order[i].getData().size(); j++) {
                PriceItem item = new PriceItem("1", "1", "",1.0, 1, 1, 1, null);
                order[i].getData().get(j).setSftp(List.of(item));
            }
        }
        OrderList list = new OrderList(order);
        Statistics stat = new Statistics(List.of(list));

        Map<String, List<OrderLog>> separ = separateByShop(List.of(order));
        for (String shop : separ.keySet()) {
            System.out.println("Shop: " + shop);
            for (OrderLog log : separ.get(shop)) {
                System.out.println(log);
            }
        }

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
