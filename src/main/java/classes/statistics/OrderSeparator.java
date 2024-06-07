package classes.statistics;

import classes.order.OrderList;
import classes.order.OrderLog;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class OrderSeparator {
    private static final Logger logger = LoggerFactory.getLogger(OrderSeparator.class);

    private List<List<OrderLog>> separatedOrders = new ArrayList<>();

    private Map<String, List<OrderLog>> separateByShop(List<OrderLog> logs) {
        Map<String, List<OrderLog>> logsByShop = new HashMap<>();

        for (OrderLog log : logs) {
            logsByShop
                    .computeIfAbsent(log.getId_shop(), k -> new ArrayList<>())
                    .add(log);
        }

        return logsByShop;
    }

    private void addToList(OrderLog[] orderLog) {
        Map<String, List<OrderLog>> separ = separateByShop(List.of(orderLog));
        for (String shop : separ.keySet()) {
            this.separatedOrders.add(separ.get(shop));
        }
    }
    public OrderSeparator(List<OrderList> enteredOrders) {
        for (OrderList log: enteredOrders) {
            addToList(log.getOrder());
        }
        logger.info("Entered orders size: " + enteredOrders.size());
        logger.info("Separated orders size: " + this.separatedOrders.size());
    }
}
