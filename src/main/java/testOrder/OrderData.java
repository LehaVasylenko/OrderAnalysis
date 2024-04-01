package testOrder;

import java.util.Objects;

public class OrderData {
    private String shopId;
    private String drugId;
    private boolean baseTest;

    @Override
    public String toString() {
        return "{\n" +
                "\tshopId: " + shopId + "\n" +
                "\tdrugId: " + drugId + "\n" +
                "}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderData)) return false;
        OrderData orderData = (OrderData) o;
        return Objects.equals(getShopId(), orderData.getShopId()) && Objects.equals(getDrugId(), orderData.getDrugId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getShopId(), getDrugId());
    }

    public String getShopId() {
        return shopId;
    }

    public String getDrugId() {
        return drugId;
    }

    public OrderData(String shopId, String drugId, boolean baseTest) {
        this.shopId = shopId;
        this.drugId = drugId;
        this.baseTest = baseTest;
    }

    public boolean isBaseTest() {
        return baseTest;
    }
}
