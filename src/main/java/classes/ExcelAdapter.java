package classes;

import classes.adapter.RequestPrepsInShop;
import classes.order.Datum;
import classes.order.OrderList;
import classes.order.OrderLog;
import interfaces.IDrugInfo;
import interfaces.IExcelAdapter;
import interfaces.IPrepShop;
import interfaces.ISftpManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import specs.Spec;

import java.util.*;

public class ExcelAdapter extends Spec implements IExcelAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ExcelAdapter.class);
    private final Set<RequestPrepsInShop> requestPrepsInShops = new HashSet<>();
    private final Set<String> requestDrugsInfo = new HashSet<>();
    private List<List<OrderList>> adaptedOrders = new ArrayList<>();

    public ExcelAdapter(List<OrderList> orders, ISftpManager download) {
        getRequestForApi(orders);

        IPrepShop prepsInShop = new PrepsByShop(this.requestPrepsInShops);
        IDrugInfo drugsInfo = new DrugsInfoMany(new ArrayList<>(this.requestDrugsInfo));

        for(OrderList order: orders) {
            OrderLog[] orderLog = order.getOrder();
            for (OrderLog log : orderLog) {
                log.setShopInfo(GetShops.getInstance().getShopById(log.getId_shop()));
                ArrayList<Datum> itemsInOrder = log.getData();
                try {
                    for (Datum items : itemsInOrder) {
                        items.setApi(prepsInShop.getPrepByShopAndId(log.getId_shop(), items.getId()));
                        items.setSftp(download.getItemByShopAndId(log.getId_shop(), items.getId()));
                        items.setDrugInfo(drugsInfo.getDrugData(items.getId()));
                    }
                } catch (Exception e) {
                    logger.error("Order {}: Datum is null: {}", log.getId_order(), e.getMessage());
                }
            }
        }

        this.adaptedOrders = trimByCorp(orders);
    }

    private List<List<OrderList>> trimByCorp(List<OrderList> orderLists) {
        Map<String, List<OrderList>> mapByCorp = new HashMap<>();

        // Group OrderList objects by idCorp
        for (OrderList orderList : orderLists) {
            String idCorp = orderList.getIdCorp();
            if (!mapByCorp.containsKey(idCorp)) {
                mapByCorp.put(idCorp, new ArrayList<>());
            }
            mapByCorp.get(idCorp).add(orderList);
        }

        // Convert map values (lists) to List<List<OrderList>>
        return new ArrayList<>(mapByCorp.values());
    }

    private void getRequestForApi(List<OrderList> orders) {
        for(OrderList order: orders) {
            OrderLog[] orderLog = order.getOrder();
            for (OrderLog log : orderLog) {
                ArrayList<Datum> itemsInOrder = log.getData();
                try{
                    for (Datum items : itemsInOrder) {
                        requestPrepsInShops.add(new RequestPrepsInShop(log.getId_shop(), items.getId()));
                        requestDrugsInfo.add(items.getId());
                    }
                } catch (Exception e) {
                    logger.error("Order {}: Datum is null: {}", log.getId_order(), e.getMessage());
                }
            }
        }
    }

    @Override
    public List<List<OrderList>> getAdaptedOrders() {
        return adaptedOrders;
    }

    public List<String> getOrdersWereShown() {
        Set<String> result = new HashSet<>();
        for (List<OrderList> orders: this.adaptedOrders) {
            for (OrderList order: orders) {
                OrderLog[] log = order.getOrder();
                for (int i = 0; i < log.length; i++) {
                    result.add(log[i].getId_order());
                }
            }
        }
        return new ArrayList<>(result);
    }
}
