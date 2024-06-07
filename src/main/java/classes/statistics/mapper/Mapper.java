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
                .idOrder(NullSafe.safeString(order.getId_order()))
                .idOrderExt(NullSafe.safeString(order.getExt_id_order()))
                .idShop(NullSafe.safeString(order.getId_shop()))
                .idShopExt(NullSafe.safeString(order.getExt_id_shop()))
                .idCorp(NullSafe.safeString(GetShops.getInstance().getCorpById(order.getId_shop())))
                .shipping(NullSafe.safeString(order.getShipping()))
                .agent(NullSafe.safeString(order.getAgent()))
                .phone(NullSafe.safeString(order.getPhone()))
                .build();
    }

    public static DatumEntity datumToEntity (OrderLog order, OrderEntity orderEntity) {
        return DatumEntity.builder()
                .state(NullSafe.safeString(order.getState()))
                .timestamp(new Date(order.getTimestamp() * 1000L))
                .order(orderEntity)
                .build();
    }

    public static DrugsEntity drugsToEntity(Datum drugList, DatumEntity datumEntity) {
        return DrugsEntity.builder()
                .datum(datumEntity)
                .drugId(NullSafe.safeString(drugList.getId()))
                .drugIdExt(NullSafe.safeString(drugList.getExt_id()))
                .quant(NullSafe.safeString(String.valueOf(drugList.getQuant())))
                .price(NullSafe.safeString(String.valueOf(drugList.getPrice())))
                .sftp(NullSafe.safeString(drugList.getNameByIdInSftp(drugList.getExt_id())))
                .drugInfo(NullSafe.safeString(drugList.getDrugInfo()))
                .build();
    }
}
