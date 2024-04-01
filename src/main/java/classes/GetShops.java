package classes;

import classes.shops.ShowShops;
import interfaces.IGetShop;
import io.restassured.RestAssured;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import specs.Spec;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static io.restassured.http.ContentType.JSON;

public class GetShops extends Spec implements IGetShop {
    private static final Logger logger = LoggerFactory.getLogger(GetShops.class);
    private final ShowShops[] shops;
    private static final GetShops INSTANCE = new GetShops();
    private static final String GAMMA_55 = "3979992";

    public static GetShops getInstance() {
        return INSTANCE;
    }

    private GetShops() {
        //initiate a path
        final String URL_BASE = "https://api.geoapteka.com.ua/show-shops";
        //initiate a constructor
        InstallSpec(reqSpec(URL_BASE,""), respSpec());
        //request to Axioma
        this.shops = RestAssured
                .given()
                .header("Connection","keep-alive")
                .header("Accept","*/*")
                .header("Accept-Encoding","gzip")
                .contentType(JSON)
                .when()
                .get()
                .then()
                .extract().response().as(ShowShops[].class);
        logger.info("ShowShops count: " + this.shops.length);
    }

    private boolean isFreshPrice (String date) {
        if (date.isEmpty()) {
            return false;
        } else {
            // Define the formatter to match the datetime pattern
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            // Parse the string to LocalDateTime
            LocalDateTime datetime = LocalDateTime.parse(date, formatter);
            // Get the current date at midnight
            LocalDateTime todayMidnight = LocalDateTime.now()
                    .minusDays(1)
                    .withHour(0)
                    .withMinute(0)
                    .withSecond(0)
                    .withNano(0);
            // Compare datetime with today midnight
            return datetime.isAfter(todayMidnight);
        }
    }

    public List<ShowShops> getShops() {
        return Arrays.stream(this.shops)
                .filter(s -> s.getBkng() == 1)
                .filter(s -> isFreshPrice(s.getUp_date()))
                .collect(Collectors.toList());
    }

    public ShowShops getShopById(String addressId) {
        return getShops()
                .stream()
                .filter(s -> s.getId().equals(addressId))
                .findFirst()
                .orElse(new ShowShops());
    }

    public List<ShowShops> getShopsByCorp(String corpId) {
        return Arrays.stream(this.shops)
                .filter(s -> s.getBkng() == 1)
                .filter(s -> isFreshPrice(s.getUp_date()))
                .filter(s -> s.getId_corp().equals(corpId))
                .collect(Collectors.toList());
    }

    public boolean isGammaShop (String addressId) {
        return getShopsByCorp(GAMMA_55)
                .stream()
                .anyMatch(shop -> shop.getId().equals(addressId));
    }

    public String getCorpById (String addressId) {
        String result = "";
        for (ShowShops shop: this.getShops()) {
            if (shop.getId().equalsIgnoreCase(addressId)) {
                result = shop.getCorp_ua();
                break;
            }
        }
        return result;
    }
}
