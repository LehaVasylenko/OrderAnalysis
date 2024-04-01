package classes.order;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderListResponse {
    private boolean ignore;
    private String ignoreReason;
    private String order_id_c;
    private String date_entered;
    private String client;
    private String phone_c;
    private String delivery_c;
    private String bill_atr_c;
    private String date_modified;
    private String agent_c;
    private String user_name;
    private String id_shop_c;
    private String step_c;
    private String order_name;
    private String shop_name_c;
    private String shop_address_c;
    private String shop_open_hours_c;
    private String shop_phone_c;
    private String product_unit_price;
    private String product_qty;
    private String product_total_price;

    public String getIgnoreReason() {
        return ignoreReason;
    }

    public void setIgnoreReason(String ignoreReason) {
        this.ignoreReason = ignoreReason;
    }

    public OrderListResponse(@JsonProperty("order_id_c") String order_id_c,
                             @JsonProperty("date_entered") String date_entered,
                             @JsonProperty("client") String client,
                             @JsonProperty("phone_c") String phone_c,
                             @JsonProperty("delivery_c") String delivery_c,
                             @JsonProperty("bill_atr_c") String bill_atr_c,
                             @JsonProperty("date_modified") String date_modified,
                             @JsonProperty("agent_c") String agent_c,
                             @JsonProperty("user_name") String user_name,
                             @JsonProperty("id_shop_c") String id_shop_c,
                             @JsonProperty("step_c") String step_c,
                             @JsonProperty("order_name") String order_name,
                             @JsonProperty("shop_name_c") String shop_name_c,
                             @JsonProperty("shop_address_c") String shop_address_c,
                             @JsonProperty("shop_open_hours_c") String shop_open_hours_c,
                             @JsonProperty("shop_phone_c") String shop_phone_c,
                             @JsonProperty("product_unit_price") String product_unit_price,
                             @JsonProperty("product_qty") String product_qty,
                             @JsonProperty("product_total_price") String product_total_price) {

        this.order_id_c = order_id_c;
        this.date_entered = date_entered;
        this.client = client;
        this.phone_c = phone_c;
        this.delivery_c = delivery_c;
        this.bill_atr_c = bill_atr_c;
        this.date_modified = date_modified;
        this.agent_c = agent_c;
        this.user_name = user_name;
        this.id_shop_c = id_shop_c;
        this.step_c = step_c;
        this.order_name = order_name;
        this.shop_name_c = shop_name_c;
        this.shop_address_c = shop_address_c;
        this.shop_open_hours_c = shop_open_hours_c;
        this.shop_phone_c = shop_phone_c;
        this.product_unit_price = product_unit_price;
        this.product_qty = product_qty;
        this.product_total_price = product_total_price;
    }

    public boolean isIgnore() {
        return ignore;
    }
    public boolean getIgnore() {
         return this.ignore; }
    public void setIgnore() {
        this.ignore = true;
    }

    public String getOrder_id_c() {
        return order_id_c;
    }

    public void setOrder_id_c(String order_id_c) {
        this.order_id_c = order_id_c;
    }

    public String getDate_entered() {
        return date_entered;
    }

    public void setDate_entered(String date_entered) {
        this.date_entered = date_entered;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getPhone_c() {
        return phone_c;
    }

    public void setPhone_c(String phone_c) {
        this.phone_c = phone_c;
    }

    public String getDelivery_c() {
        return delivery_c;
    }

    public void setDelivery_c(String delivery_c) {
        this.delivery_c = delivery_c;
    }

    public String getBill_atr_c() {
        return bill_atr_c;
    }

    public void setBill_atr_c(String bill_atr_c) {
        this.bill_atr_c = bill_atr_c;
    }

    public String getDate_modified() {
        return date_modified;
    }

    public void setDate_modified(String date_modified) {
        this.date_modified = date_modified;
    }

    public String getAgent_c() {
        return agent_c;
    }

    public void setAgent_c(String agent_c) {
        this.agent_c = agent_c;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getId_shop_c() {
        return id_shop_c;
    }

    public void setId_shop_c(String id_shop_c) {
        this.id_shop_c = id_shop_c;
    }

    public String getStep_c() {
        return step_c;
    }

    public void setStep_c(String step_c) {
        this.step_c = step_c;
    }

    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public String getShop_name_c() {
        return shop_name_c;
    }

    public void setShop_name_c(String shop_name_c) {
        this.shop_name_c = shop_name_c;
    }

    public String getShop_address_c() {
        return shop_address_c;
    }

    public void setShop_address_c(String shop_address_c) {
        this.shop_address_c = shop_address_c;
    }

    public String getShop_open_hours_c() {
        return shop_open_hours_c;
    }

    public void setShop_open_hours_c(String shop_open_hours_c) {
        this.shop_open_hours_c = shop_open_hours_c;
    }

    public String getShop_phone_c() {
        return shop_phone_c;
    }

    public void setShop_phone_c(String shop_phone_c) {
        this.shop_phone_c = shop_phone_c;
    }

    public String getProduct_unit_price() {
        return product_unit_price;
    }

    public void setProduct_unit_price(String product_unit_price) {
        this.product_unit_price = product_unit_price;
    }

    public String getProduct_qty() {
        return product_qty;
    }

    public void setProduct_qty(String product_qty) {
        this.product_qty = product_qty;
    }

    public String getProduct_total_price() {
        return product_total_price;
    }

    public void setProduct_total_price(String product_total_price) {
        this.product_total_price = product_total_price;
    }

    public OrderListResponse() {
    }
}
