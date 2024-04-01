package classes.order;

import classes.adapter.PrepsInShop;
import classes.shops.ShowShops;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import sftpreader.shopPrice.PriceItem;
import sftpreader.shopPrice.ShopPrice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class OrderLog {
    //additional data for analysis
    private ShowShops shopInfo;
    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    //date from response
    private String id_shop;
    private String ext_id_shop;
    private String shop_start_time;
    private String shop_finish_time;
    private String phone;
    private boolean test;
    @JsonProperty("MsgFlags")
    private int msgFlags;
    private long timestamp;
    private String agent;
    private String id_order;
    private String ext_id_order;
    private String shipping = "";
    private String delivery_date;
    private String delivery_time;
    private String state = "";
    private String reason = "";
    private String attribute;
    private ArrayList<Datum> data;

    public ShowShops getShopInfo() {
        return shopInfo;
    }

    public void setShopInfo(ShowShops shopInfo) {
        this.shopInfo = shopInfo;
    }

    public String convertTimestamp() {
        Date date = new Date(this.timestamp * 1000L);
        // Create a SimpleDateFormat object with the desired format
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM - HH:mm:ss");
        // Format the date
        return sdf.format(date);
    }
    public OrderLog() {
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }

    public String getId_shop() {
        return id_shop;
    }

    public void setId_shop(String id_shop) {
        this.id_shop = id_shop;
    }

    public String getExt_id_shop() {
        return ext_id_shop;
    }

    public void setExt_id_shop(String ext_id_shop) {
        this.ext_id_shop = ext_id_shop;
    }

    public String getShop_start_time() {
        return shop_start_time;
    }

    public void setShop_start_time(String shop_start_time) {
        this.shop_start_time = shop_start_time;
    }

    public String getShop_finish_time() {
        return shop_finish_time;
    }

    public void setShop_finish_time(String shop_finish_time) {
        this.shop_finish_time = shop_finish_time;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isTest() {
        return test;
    }

    public void setTest(boolean test) {
        this.test = test;
    }

    public int getMsgFlags() {
        return msgFlags;
    }

    public void setMsgFlags(int msgFlags) {
        this.msgFlags = msgFlags;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getId_order() {
        return id_order;
    }

    public void setId_order(String id_order) {
        this.id_order = id_order;
    }

    public String getExt_id_order() {
        return ext_id_order;
    }

    public void setExt_id_order(String ext_id_order) {
        this.ext_id_order = ext_id_order;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public ArrayList<Datum> getData() {
        return data;
    }

    public void setData(ArrayList<Datum> data) {
        this.data = data;
    }

    public OrderLog(String id_shop, String ext_id_shop, String shop_start_time, String shop_finish_time, String phone, boolean test, int msgFlags, int timestamp, String agent, String id_order, String ext_id_order, String shipping, String delivery_date, String delivery_time, String state, String reason, String attribute, ArrayList<Datum> data) {
        this.id_shop = id_shop;
        this.ext_id_shop = ext_id_shop;
        this.shop_start_time = shop_start_time;
        this.shop_finish_time = shop_finish_time;
        this.phone = phone;
        this.test = test;
        this.msgFlags = msgFlags;
        this.timestamp = timestamp;
        this.agent = agent;
        this.id_order = id_order;
        this.ext_id_order = ext_id_order;
        this.shipping = shipping;
        this.delivery_date = delivery_date;
        this.delivery_time = delivery_time;
        this.state = state;
        this.reason = reason;
        this.attribute = attribute;
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderLog)) return false;
        OrderLog that = (OrderLog) o;
        return Objects.equals(getId_shop(), that.getId_shop()) && Objects.equals(getExt_id_shop(), that.getExt_id_shop()) && Objects.equals(getPhone(), that.getPhone()) && Objects.equals(getAgent(), that.getAgent()) && Objects.equals(getShipping(), that.getShipping()) && Objects.equals(getData(), that.getData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId_shop(), getExt_id_shop(), getPhone(), getAgent(), getShipping(), getData());
    }
}
