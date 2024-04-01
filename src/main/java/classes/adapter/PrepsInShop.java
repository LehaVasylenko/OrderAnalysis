package classes.adapter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PrepsInShop {
    private String id_drug;
    private String id_drug_ex;
    private String id_shop;
    private String id_shop_ex;
    private Double quant;
    private Double price;
    private double price_cntr;
    private int pfactor;
    private String shop_logo;

    public PrepsInShop(String id_drug, String id_drug_ex, String id_shop, String id_shop_ex, Double quant, Double price, double price_cntr, int pfactor, String shop_logo) {
        this.id_drug = id_drug;
        this.id_drug_ex = id_drug_ex;
        this.id_shop = id_shop;
        this.id_shop_ex = id_shop_ex;
        this.quant = quant;
        this.price = price;
        this.price_cntr = price_cntr;
        this.pfactor = pfactor;
        this.shop_logo = shop_logo;
    }

    public PrepsInShop() {
    }

    public String getId_drug() {
        return id_drug;
    }

    public void setId_drug(String id_drug) {
        this.id_drug = id_drug;
    }

    public String getId_drug_ex() {
        return id_drug_ex;
    }

    public void setId_drug_ex(String id_drug_ex) {
        this.id_drug_ex = id_drug_ex;
    }

    public String getId_shop() {
        return id_shop;
    }

    public void setId_shop(String id_shop) {
        this.id_shop = id_shop;
    }

    public String getId_shop_ex() {
        return id_shop_ex;
    }

    public void setId_shop_ex(String id_shop_ex) {
        this.id_shop_ex = id_shop_ex;
    }

    public Double getQuant() {
        return quant;
    }

    public void setQuant(Double quant) {
        this.quant = quant;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public double getPrice_cntr() {
        return price_cntr;
    }

    public void setPrice_cntr(double price_cntr) {
        this.price_cntr = price_cntr;
    }

    public int getPfactor() {
        return pfactor;
    }

    public void setPfactor(int pfactor) {
        this.pfactor = pfactor;
    }

    public String getShop_logo() {
        return shop_logo;
    }

    public void setShop_logo(String shop_logo) {
        this.shop_logo = shop_logo;
    }
}
