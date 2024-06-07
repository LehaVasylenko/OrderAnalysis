package classes;

import classes.order.Datum;
import classes.order.OrderList;
import classes.order.OrderLog;
import classes.statistics.OrderSeparator;
import classes.statistics.entity.DatumEntity;
import classes.statistics.entity.OrderEntity;
import classes.statistics.mapper.Mapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Statistics {
    private static final Logger logger = LoggerFactory.getLogger(Statistics.class);

    private List<List<OrderLog>> orders = new ArrayList<>();

    public Statistics (List<OrderList> enteredOrders) {
        OrderSeparator separator = new OrderSeparator(enteredOrders);
        this.orders = separator.getSeparatedOrders();
        writeToDB();
    }

    private void writeToDB() {
        Configuration config = new Configuration();
        config.configure();

        try (SessionFactory sessionFactory = config.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

//            int batchSize = 20;
//            int count = 0;

            for (List<OrderLog> logByshop: this.orders) {
                OrderEntity orderEntity = Mapper.orderToEntity(logByshop.get(0));
                session.save(orderEntity);
                for (OrderLog logItem: logByshop) {
                    DatumEntity datumEntity = Mapper.datumToEntity(logItem, orderEntity);
                    session.save(datumEntity);
                    for (Datum drug: logItem.getData()) {
                        session.save(Mapper.drugsToEntity(drug, datumEntity));
                    }
                }
//                if (++count % batchSize == 0) {
//                    session.flush();
//                    session.clear();
//                }
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error("Error writing to database: {}", e.getMessage());
        }
    }
}
