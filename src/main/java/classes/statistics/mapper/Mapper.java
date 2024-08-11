package classes.statistics.mapper;

import classes.GetShops;
import classes.order.Datum;
import classes.order.OrderLog;
import classes.shops.ShowShops;
import classes.statistics.entity.DatumEntity;
import classes.statistics.entity.DrugsEntity;
import classes.statistics.entity.OrderEntity;

import java.time.*;
import java.time.format.TextStyle;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Mapper {

    public static OrderEntity orderToEntity (OrderLog order) {
        return OrderEntity.builder()
                .idOrder(NullSafe.safeString(order.getId_order()))
                .idOrderExt(NullSafe.safeString(order.getExt_id_order()))
                .idShop(NullSafe.safeString(order.getId_shop()))
                .idShopExt(NullSafe.safeString(order.getExt_id_shop()))
                .idCorp(NullSafe.safeString(GetShops.getInstance().getCorpById(order.getId_shop())))
                .shipping(NullSafe.safeString(order.getShipping()))
                .workingTime(isWorkingTime(GetShops.getInstance().getShopById(order.getId_shop()), order.getTimestamp()))
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
                .build();
    }

    private static boolean isWorkingTime(ShowShops shop, Long timestamp) {
        try {
            return isPharmacyOpen(parseSingleSchedule(shop.getOpen_hours()), timestamp);
        } catch (NullPointerException e) {
            return false;
        }
    }

    private static Map<String, Map<String, String>> parseSingleSchedule(String schedule) {
        String patternString = "([A-Za-z, -]+) (\\d{2}:\\d{2})-(\\d{2}:\\d{2})";
        Pattern pattern = Pattern.compile(patternString);

        Map<String, Map<String, String>> scheduleMap = new HashMap<>();
        List<String> daysOfWeek = Arrays.asList("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su");

        Matcher matcher = pattern.matcher(schedule);

        while (matcher.find()) {
            String days = matcher.group(1).replace(" ", "");
            String start = matcher.group(2);
            String end = matcher.group(3);

            for (String day : days.split(",")) {
                if (day.contains("-")) {
                    String[] dayRange = day.split("-");
                    int startIndex = daysOfWeek.indexOf(dayRange[0]);
                    int endIndex = daysOfWeek.indexOf(dayRange[1]);

                    for (int i = startIndex; i <= endIndex; i++) {
                        scheduleMap.put(daysOfWeek.get(i), createScheduleMap(start, end));
                    }
                } else {
                    scheduleMap.put(day, createScheduleMap(start, end));
                }
            }
        }

        return scheduleMap;
    }

    private static Map<String, String> createScheduleMap(String start, String end) {
        Map<String, String> timeMap = new HashMap<>();
        timeMap.put("start", start);
        if (end.equals("00:00")) timeMap.put("end", "23:59");
        else timeMap.put("end", end);
        return timeMap;
    }

    private static boolean isPharmacyOpen(Map<String, Map<String, String>> schedule, Long timestamp) {
        LocalDateTime dateTime = Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalTime time = dateTime.toLocalTime();

        String day = dateTime.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.US).substring(0, 2);
        Map<String, String> todaySchedule = schedule.get(day);

        if (todaySchedule == null) {
            return false;
        }

        LocalTime start = LocalTime.parse(todaySchedule.get("start"));
        LocalTime end = LocalTime.parse(todaySchedule.get("end"));
        if (start.equals(end)) return true;

        return !time.isBefore(start) && !time.isAfter(end);
    }
}
