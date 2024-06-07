import classes.*;
import interfaces.IOrders;
import lombok.extern.slf4j.Slf4j;
import sftpreader.SftpManager;
import specs.Configurator;

@Slf4j
public class Main extends Configurator {

    public static void main(String[] args) {
        // Record the start time
        long startTime = System.currentTimeMillis();
        init();

        IOrders orders = new GetOrders(7);
        OrderFilter filter = new OrderFilter(orders.getNewOrdersNumbers());

        ExcelAdapter adapter = new ExcelAdapter(filter.getOrdersToCheck(), new SftpManager(filter.getDistinctShops()));
        ExcelWriter writer = new ExcelWriter(adapter.getAdaptedOrders());

        Statistics statistics = new Statistics(filter.getWholeOrdersToCheck());
        orders.rememberOrdersNumbers(filter.getToCheck(), filter.getIgnorable());
        // Record the end time
        long endTime = System.currentTimeMillis();
        workTime(startTime, endTime);
    }
}
