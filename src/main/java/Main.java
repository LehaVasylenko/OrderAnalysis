import classes.ExcelAdapter;
import classes.ExcelWriter;
import classes.GetOrders;
import classes.OrderFilter;
import interfaces.IOrders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sftpreader.SftpManager;
import specs.Configurator;

public class Main extends Configurator {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        // Record the start time
        long startTime = System.currentTimeMillis();
        init();

        IOrders orders = new GetOrders(7);
        OrderFilter filter = new OrderFilter(orders.getNewOrdersNumbers());

        ExcelAdapter adapter = new ExcelAdapter(filter.getOrdersToCheck(), new SftpManager(filter.getDistinctShops()));
        ExcelWriter writer = new ExcelWriter(adapter.getAdaptedOrders());

        orders.rememberOrdersNumbers(filter.getToCheck(), filter.getIgnorable());
        // Record the end time
        long endTime = System.currentTimeMillis();
        workTime(startTime, endTime);
    }
}
