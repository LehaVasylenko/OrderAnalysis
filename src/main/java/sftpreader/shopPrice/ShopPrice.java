package sftpreader.shopPrice;

import classes.ExcelWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ShopPrice {
    private static final Logger logger = LoggerFactory.getLogger(ShopPrice.class);
    private String shopId;
    private Long priceTime;
    private List<PriceItem> priceList;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public Long getPriceTime() {
        return priceTime;
    }

    public void setPriceTime(Long priceTime) {
        this.priceTime = priceTime;
    }

    public List<PriceItem> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<PriceItem> priceList) {
        this.priceList = priceList;
    }

    @Override
    public String toString() {
        return "{\n" +
                "\tshopId: " + shopId + "\n" +
                "\tpriceTime: " + priceTime + "\n" +
                "\tpriceList: " + priceList + "\n" +
                "}\n";
    }

    public ShopPrice(String shopId) {
        this.shopId = shopId;
    }

    public List<PriceItem> getItemById (String idDrug) {
        List<PriceItem> result = new ArrayList<>();
        try {
            for (PriceItem priceItem : this.priceList) {
                if (priceItem.getLink().getId_drug().equalsIgnoreCase(idDrug)) {
                    result.add(priceItem);
                }
            }
        } catch (Exception e) {
            logger.error("Price list for " + this.shopId + " is null: " + e.getMessage());
            Link link = new Link("","","","","");
            result.add(new PriceItem(idDrug, "Price List is empty", "", 0.0, 0, 0, 0, link));
        }
        if (result.isEmpty()) {
            Link link = new Link("","","","","");
            result.add(new PriceItem(idDrug, "No such position in shop now", "", 0.0, 0, 0, 0, link));
        }

        return result;
    }
}
