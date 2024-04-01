package sftpreader.shopPrice;

import classes.ExcelWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public PriceItem getItemById (String idDrug) {
        PriceItem result = new PriceItem();
        float quant = 0;
        try {
            for (PriceItem priceItem : this.priceList) {
                if (priceItem.getId().equalsIgnoreCase(idDrug)) {
                    result = priceItem;
                    try {
                        quant += priceItem.getQuant();
                    } catch (Exception e) {
                        logger.error("Quantity field for " + priceItem.getName() + " is null: " + e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Price list for " + this.shopId + " is null: " + e.getMessage());
            Link link = new Link("","","","","");
            result = new PriceItem(idDrug, "Price List is empty", "", 0.0, 0, 0, 0, link);
        }
        if (result.getId() == null) {
            Link link = new Link("","","","","");
            result = new PriceItem(idDrug, "No such position in store", "", 0.0, 0, 0, 0, link);

        }
        if (result.getQuant() == null && result.getId() != null) {
            result.setQuant(-1.0);
        }
        if (result.getQuant() < quant) {
            result.setQuant((double) quant);
        }
        return result;
    }
}
