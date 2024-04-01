package interfaces;

import classes.order.OrderList;

import java.util.List;

@FunctionalInterface
public interface IExcelAdapter {
    List<List<OrderList>> getAdaptedOrders();
}
