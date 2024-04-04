package interfaces;

import classes.order.OrderList;

import java.util.ArrayList;
import java.util.List;

public interface IOrders {
    ArrayList<String> getNewOrdersNumbers();
    void rememberOrdersNumbers(List<String> notCompletedNew, List<String> checkedOrders);
}
