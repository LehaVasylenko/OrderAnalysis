package classes.order;

import classes.adapter.PrepsInShop;
import sftpreader.shopPrice.PriceItem;

import java.util.List;
import java.util.Objects;

public class Datum {
    private String id;
    private String ext_id;
    private double quant;
    private double price;
    private PrepsInShop api;
    private List<PriceItem> sftp;
    private long priceTime;
    private String drugInfo;

    public String getNameByIdInSftp(String id) {
        for (PriceItem item: this.sftp) {
            if (item.getId().equals(id)) {
                return item.getName();
            }
        }
        return "No Such prep";
    }

    public Datum(String id, String ext_id, double quant, double price) {
        this.id = id;
        this.ext_id = ext_id;
        this.quant = quant;
        this.price = price;
    }

    public String getDrugInfo() {
        return drugInfo;
    }

    public void setDrugInfo(String drugInfo) {
        this.drugInfo = drugInfo;
    }

    public PrepsInShop getApi() {
        return api;
    }

    public void setApi(PrepsInShop api) {
        this.api = api;
    }

    public List<PriceItem> getSftp() {
        return sftp;
    }

    public void setSftp(List<PriceItem> sftp) {
        this.sftp = sftp;
    }

    public long getPriceTime() {
        return priceTime;
    }

    public void setPriceTime(long priceTime) {
        this.priceTime = priceTime;
    }

    public Datum() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setExt_id(String ext_id) {
        this.ext_id = ext_id;
    }

    public void setQuant(double quant) {
        this.quant = quant;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }
    public String getExt_id() {
        return ext_id;
    }
    public double getQuant() {
        return quant;
    }
    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Datum)) return false;
        Datum datum = (Datum) o;
        return Double.compare(getQuant(), datum.getQuant()) == 0 && Objects.equals(getId(), datum.getId()) && Objects.equals(getExt_id(), datum.getExt_id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getExt_id(), getQuant());
    }
}
