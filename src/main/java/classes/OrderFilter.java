package classes;

import classes.order.BookingBody;
import classes.order.Datum;
import classes.order.OrderLog;
import classes.order.OrderList;
import interfaces.IGetShop;
import io.restassured.RestAssured;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import specs.Spec;
import testOrder.OrderData;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.restassured.http.ContentType.JSON;

public class OrderFilter extends Spec {
    private static final Logger logger = LoggerFactory.getLogger(OrderFilter.class);
    private final int HOURS24 = 24 * 60 * 60;
    private final int HOURS48 = HOURS24 * 2;
    private List<String> ignorable = new ArrayList<>();
    private List<String> toCheck = new ArrayList<>();
    private Set<OrderData> makeTest = new HashSet<>();
    private List<OrderList> ordersToCheck;

    public OrderFilter(@NotNull ArrayList<String> newOrders) {
        orderPreparator(newOrders);

        this.ordersToCheck.stream()
                .filter(order -> !order.isIgnore())
                .forEach(order -> {
                    OrderLog[] log = order.getOrder();
                    long timeDifference = log[log.length - 1].getTimestamp() - log[0].getTimestamp();

                    //24 hour in gamma-55
                    if (timeDifference >= HOURS24
                            && GetShops.getInstance().isGammaShop(log[0].getId_shop())
                            && log[0].getShipping().equalsIgnoreCase("pickup")) {
                        order.setIgnore("more than 24 hours in gamma-55");
                        logger.info(log[0].getId_order() + ": " + order.getIgnoreReason());
                    }

                    //48 hours in pick-up
                    if (timeDifference >= HOURS48
                            && log[0].getShipping().equalsIgnoreCase("pickup")) {
                        order.setIgnore("more than 48 hours for pickup");
                        logger.info(log[0].getId_order() + ": " + order.getIgnoreReason());
                    }

                    //cancel reason 3
                    if (Arrays.stream(log).anyMatch(logitem -> logitem.getReason().equalsIgnoreCase("3"))) {
                        order.setIgnore("client ignored");
                        logger.info(log[0].getId_order() + ": " + order.getIgnoreReason());
                    }

                    //cancel reason 1 || 2
                    IntStream.range(0, log.length).filter(i -> log[i].getReason() != null).forEach(i -> {
                        if (log[i].getReason().equalsIgnoreCase("1")) {
                            log[i].getData().forEach(oD -> makeTest.add(new OrderData(log[i].getId_shop(), oD.getId(), true)));
                        }
                        if (log[i].getReason().equalsIgnoreCase("2")) {
                            log[i].getData().forEach(oD -> makeTest.add(new OrderData(log[i].getId_shop(), oD.getId(), false)));
                        }
                    });
                });
        doubleClickFinder();
    }

    private void doubleClickFinder() {
        int time = 25*60;

        Collections.sort(this.ordersToCheck);

        //find a doubles
        for (int i = 0; i < this.ordersToCheck.size(); i++) {
            String logMessage = "";

            //get items in order
            OrderLog[] order1 = this.ordersToCheck.get(i).getOrder();
            ArrayList<Datum> items1order = order1[0].getData();

            //moving up to list
            if (!this.ordersToCheck.get(i).isIgnore()) {
                for (int j = i - 1; j > 1; j--) {
                    //get items in order
                    OrderLog[] order2 = this.ordersToCheck.get(j).getOrder();
                    ArrayList<Datum> items2order = order2[0].getData();
                    if (order1[0].equals(order2[0])
                            && items1order.get(0).equals(items2order.get(0))
                            && Arrays.stream(order2).noneMatch(o -> o.getState().equalsIgnoreCase("Canceled"))
                            && (Math.abs(order1[0].getTimestamp() - order2[0].getTimestamp()) <= time))
                    {
                        logMessage = order1[0].getAgent() + ": double click " + order1[0].getId_order() + " with ";
                        logMessage += order2[0].getId_order() + " time: " + (Math.abs(order1[0].getTimestamp() - order2[0].getTimestamp()));

                        logger.info(logMessage);
                        this.ordersToCheck.get(i).setIgnore(logMessage);
                    }
                }
            }

            //moving down to list
            if (!this.ordersToCheck.get(i).isIgnore()) {
                for (int j = i + 1; j < (this.ordersToCheck.size() - 1); j++) {
                    //get items in order
                    OrderLog[] order2 = this.ordersToCheck.get(j).getOrder();
                    ArrayList<Datum> items2order = order2[0].getData();
                    if (order1[0].equals(order2[0])
                            && items1order.get(0).equals(items2order.get(0))
                            && Arrays.stream(order2).noneMatch(o -> o.getState().equalsIgnoreCase("Canceled"))
                            && (Math.abs(order1[0].getTimestamp() - order2[0].getTimestamp()) <= time))
                    {
                        logMessage = order1[0].getAgent() + ": double click " + order1[0].getId_order() + " with ";
                        logMessage += order2[0].getId_order() + " time: " + (Math.abs(order1[0].getTimestamp() - order2[0].getTimestamp()));

                        logger.info(logMessage);
                        this.ordersToCheck.get(i).setIgnore(logMessage);
                    }
                }
            }
        }
    }
    private List<List<String>> splitList(List<String> originalList) {
        int chunkSize = originalList.size() / Runtime.getRuntime().availableProcessors();
        List<List<String>> resultList = new ArrayList<>();
        int startIndex = 0;
        for (int i = 0; i < 4; i++) {
            int endIndex = Math.min(startIndex + chunkSize, originalList.size());
            resultList.add(originalList.subList(startIndex, endIndex));
            startIndex = endIndex;
        }
        return resultList;
    }

    private void orderPreparator(ArrayList<String> newOrders) {
        this.ordersToCheck = new ArrayList<>(newOrders.size());
        List<Thread> orderThread = new ArrayList<>(Runtime.getRuntime().availableProcessors());

        for (List<String> sublist: splitList(newOrders)) {
            Runnable orders = () -> {
                for (int j = 0; j < sublist.size(); j++) {
                    String message = "";
                    OrderLog[] orderLog = getOrderInfo(sublist.get(j));

                    //canceled orders and test = false
                    if (Arrays.stream(orderLog).filter(ol -> !ol.isTest())
                            .anyMatch(ol -> ol.getState().equals("Canceled"))) {
                        message = j + ":" + newOrders.size() + ":" + newOrders.get(j) + ": " + orderLog[orderLog.length - 1].getShipping() + ": " + orderLog[orderLog.length - 1].getState() + " received at " + orderLog[orderLog.length - 1].convertTimestamp();
                        logger.info(message);

                        this.ordersToCheck.add(new OrderList(GetShops.getInstance().getCorpById(orderLog[0].getId_shop()), orderLog));

                        //next starts will ignore this order
                        this.ignorable.add(newOrders.get(j));
                    }

                    //completed orders and test = false
                    if (Arrays.stream(orderLog).filter(ol -> !ol.isTest())
                            .anyMatch(ol -> ol.getState().equals("Completed"))) {
                        message = j + ":" + newOrders.size() + ":" + newOrders.get(j) + ": " + orderLog[orderLog.length - 1].getShipping() + ": " + orderLog[orderLog.length - 1].getState() + " received at " + orderLog[orderLog.length - 1].convertTimestamp();
                        logger.info(message);

                        OrderList temp = new OrderList(orderLog);
                        temp.setIgnore(message);
                        this.ordersToCheck.add(temp);

                        //next starts will ignore this order
                        this.ignorable.add(newOrders.get(j));
                    }

                    //intermediate state orders and test = false
                    if (Arrays.stream(orderLog).filter(ol -> !ol.isTest())
                            .noneMatch(ol -> ol.getState().equals("Completed") || ol.getState().equals("Canceled"))) {
                        message = j + ":" + newOrders.size() + ":" + newOrders.get(j) + ": " + orderLog[orderLog.length - 1].getShipping() + ": " + orderLog[orderLog.length - 1].getState() + " received at " + orderLog[orderLog.length - 1].convertTimestamp();
                        logger.info(message);

                        OrderList temp = new OrderList(orderLog);
                        temp.setIgnore(message);
                        this.ordersToCheck.add(temp);

                        //next starts will NOT ignore this order
                        this.toCheck.add(newOrders.get(j));
                    }
                }
            };

            Thread temp = new Thread(orders);
            temp.start();
            orderThread.add(temp);
        }

        for (Thread thread: orderThread) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                logger.error("Order thread Interrupted Error: {}", e.getMessage());
            }
        }
    }

    public Set<OrderData> getMakeTest() {
        return makeTest;
    }

    public List<String> getDistinctShops() {
        Set<String> distinctShops = new HashSet<>();

        this.ordersToCheck.stream()
                .filter(order -> !order.isIgnore())
                .forEach(order -> Arrays.stream(order.getOrder())
                        .forEach(orderLog -> distinctShops.add(orderLog.getId_shop())));

        return new ArrayList<>(distinctShops);
    }

    public List<OrderList> getOrdersToCheck() {
        return this.ordersToCheck.stream()
                .filter(order -> !order.isIgnore())
                .collect(Collectors.toList());
    }

    public List<String> getIgnorable() {
        return ignorable;
    }

    public List<String> getToCheck() {
        return toCheck;
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
                .extract().response().as(OrderLog[].class);
    }

    private boolean freshOrder (long timestamp) {
        LocalDateTime orderTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault());
        // Get the current date at midnight
        LocalDateTime yesterdayMidnight = LocalDateTime.now().minusDays(1)
                .withHour(0).withMinute(0).withSecond(0).withNano(0);

        return !orderTime.isBefore(yesterdayMidnight);
    }

}
