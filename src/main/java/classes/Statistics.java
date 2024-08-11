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
        String temp = null;
        try (SessionFactory sessionFactory = config.buildSessionFactory()) {
            for (List<OrderLog> logByshop : this.orders) {
                if (logByshop.get(logByshop.size() - 1).getState().equals("Completed") || logByshop.get(logByshop.size() - 1).getState().equals("Canceled")) {
                    try (Session session = sessionFactory.openSession()) {
                        session.beginTransaction();
                        OrderEntity orderEntity = Mapper.orderToEntity(logByshop.get(0));
                        temp = orderEntity.toString();
                        session.save(orderEntity);
                        for (OrderLog logItem : logByshop) {
                            DatumEntity datumEntity = Mapper.datumToEntity(logItem, orderEntity);
                            temp = datumEntity.toString();
                            session.save(datumEntity);
                            for (Datum drug : logItem.getData()) {
                                session.save(Mapper.drugsToEntity(drug, datumEntity));
                            }
                        }
                        session.getTransaction().commit();
                    } catch (Exception e) {
                        logger.error("Error writing order {} to database: {}", logByshop.get(0).getId_order(), e.getMessage());
                        logger.error(temp);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error initializing SessionFactory: {}", e.getMessage());
        }
    }

}
