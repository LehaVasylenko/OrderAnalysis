package classes.statistics.mapper;

import classes.GetShops;
import classes.order.Datum;
import classes.order.OrderLog;
import classes.statistics.entity.DatumEntity;
import classes.statistics.entity.DrugsEntity;
import classes.statistics.entity.OrderEntity;

import java.util.Date;

public class Mapper {

    public static OrderEntity orderToEntity (OrderLog order) {
        return OrderEntity.builder()
                .idOrder(order.getId_order())
                .idOrderExt(order.getExt_id_order())
                .idShop(order.getId_shop())
                .idShopExt(order.getExt_id_shop())
                .idCorp(GetShops.getInstance().getCorpById(order.getId_shop()))
                .shipping(order.getShipping())
                .agent(order.getAgent())
                .phone(order.getPhone())
                .build();
    }

    public static DatumEntity datumToEntity (OrderLog order, OrderEntity orderEntity) {
        return DatumEntity.builder()
                .state(order.getState())
                .timestamp(new Date(order.getTimestamp() * 1000L))
                .order(orderEntity)
                .build();
    }

    public static DrugsEntity drugsToEntity(Datum drugList, DatumEntity datumEntity) {
        return DrugsEntity.builder()
                .datum(datumEntity)
                .drugId(drugList.getId())
                .drugIdExt(drugList.getExt_id())
                .quant(String.valueOf(drugList.getQuant()))
                .price(String.valueOf(drugList.getPrice()))
                .sftp(drugList.getNameByIdInSftp(drugList.getExt_id()))
                .drugInfo(drugList.getDrugInfo())
                .build();
    }
}
