package classes.order;

import org.jetbrains.annotations.NotNull;

public class OrderList implements Comparable<OrderList> {
    private boolean ignore;
    private String ignoreReason;
    private String idCorp;
    private OrderLog[] order;
    public OrderList(OrderLog[] order) {
        this.ignore = false;
        this.order = order;
    }
    public OrderList() {
    }
    public boolean isIgnore() {
        return ignore;
    }
    public void setIgnore(String ignoreReason) {
        this.ignore = true;
        this.ignoreReason = ignoreReason;
    }
    public String getIgnoreReason() {
        return ignoreReason;
    }
    public OrderLog[] getOrder() {
        return order;
    }
    public void setOrder(OrderLog[] order) {
        this.order = order;
    }
    @Override
    public int compareTo(@NotNull OrderList o) {
        OrderLog[] oLog1 = this.getOrder();
        OrderLog[] oLog2 = o.getOrder();
        return (int) (oLog1[0].getTimestamp() - oLog2[0].getTimestamp());
    }

   public String getIdCorp() {
        return idCorp;
    }

    public OrderList(String idCorp, OrderLog[] order) {
        this.idCorp = idCorp;
        this.order = order;
    }
}
