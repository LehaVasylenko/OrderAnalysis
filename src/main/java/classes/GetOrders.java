package classes;

import classes.order.OrderListResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import interfaces.IOrders;
import io.restassured.RestAssured;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import specs.Spec;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static fileWorks.OrdersFiles.readOrdersFromFile;
import static fileWorks.OrdersFiles.writeOrdersToFile;
import static io.restassured.http.ContentType.JSON;

public class GetOrders extends Spec implements IOrders {
    private final int CAPACITY = 1000;
    //for completed orders, old canceled orders, etc
    private List<String> ignoredOrders = new ArrayList<>(CAPACITY * 2);

    //for not completed orders were found in previous iterations
    private List<String> notCompletedOrders = new ArrayList<>(CAPACITY);
    private static final Logger logger = LoggerFactory.getLogger(GetOrders.class);
    private ArrayList<String> newOrdersNumbers = new ArrayList<>(CAPACITY);
    private String dateStart;
    private String dateEnd;

    private void getDates(int date) {
        // Get the current date
        LocalDate end = LocalDate.now();

        // Get the date for yesterday
        LocalDate start = end.minusDays(date);

        // Format yesterday's date as a string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        dateStart = start.format(formatter);
        dateEnd = end.format(formatter);

        // Print the result
        logger.info("Start Date: " + dateStart);
        logger.info("End Date: " + dateEnd);
    }

    public GetOrders(int daysBack) {

        this.getDates(daysBack);

        HashSet<String> unique = new HashSet<>();

        final String URL_BASE = "https://suitecrm.morion.ua/service/v4_1/rest.php";
        String restData = "{\"date_begin\":\"" + dateStart + "\",\"date_end\":\"" + dateEnd + "\"}";
        //initiate a constructor
        InstallSpec(reqSpec(URL_BASE,""), respSpec());
        //request to suitecrm
        String response = RestAssured
                .given()
                .param("method", "order_by_date")
                .param("input_type", "JSON")
                .param("respone_type", "JSON")
                .param("rest_data", restData)
                .header("Connection","keep-alive")
                .header("Accept","*/*")
                .header("Accept-Encoding","gzip, deflate, br")
                .contentType(JSON)
                .when()
                .get()
                .then()
                .extract().response().asString();

        ObjectMapper objectMapper = new ObjectMapper();
        String[] jsonLines = response.substring(1, response.length() - 3).split("\"},");

        OrderListResponse[] orders = new OrderListResponse[jsonLines.length];

        for (int i = 0; i < jsonLines.length; i++) {
            jsonLines[i]+="\"}";
            try {
                orders[i] = objectMapper.readValue(jsonLines[i], OrderListResponse.class);
            } catch (JsonProcessingException e) {
                logger.error(e.getMessage());
            }
            unique.add(orders[i].getOrder_id_c());
        }

        logger.info("Orders number: "+jsonLines.length);
        logger.info("Unique orders number: " + unique.size());

        this.newOrdersNumbers = new ArrayList<>(unique);
        this.newOrdersNumbers.trimToSize();
    }

    public ArrayList<String> getNewOrdersNumbers() {
        this.ignoredOrders = readOrdersFromFile(false, true);
        this.notCompletedOrders = readOrdersFromFile(true, false);

        //remove already checked orders
        this.newOrdersNumbers.removeAll(this.ignoredOrders);

        //add not completed orders for further analysis
        Set<String> temp = new HashSet<>(this.newOrdersNumbers);
        temp.addAll(this.notCompletedOrders);
        this.newOrdersNumbers = new ArrayList<>(temp);

        logger.info("Final orders number: " + this.newOrdersNumbers.size());
        return this.newOrdersNumbers;
    }

    public void rememberOrdersNumbers(List<String> notCompletedNew, List<String> checkedOrders) {
        Set<String> temp = new HashSet<>(this.ignoredOrders);
        temp.addAll(checkedOrders);
        this.ignoredOrders = new ArrayList<>(temp);

        temp = new HashSet<>(this.notCompletedOrders);
        temp.addAll(notCompletedNew);
        this.notCompletedOrders = new ArrayList<>(temp);

        writeOrdersToFile(this.ignoredOrders, false, true, false);
        writeOrdersToFile(this.notCompletedOrders, true, false, false);
    }

}
