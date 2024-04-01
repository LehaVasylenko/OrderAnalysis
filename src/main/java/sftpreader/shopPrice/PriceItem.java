package sftpreader.shopPrice;

public class PriceItem {
    private String id;
    private String name;
    private String home;
    private Double quant;
    private Float price;
    private Float price_cntr;
    private int pfactor;
    private Link link;

    public Double getQuant() {
        return quant;
    }

    public void setQuant(Double quant) {
        this.quant = quant;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    @Override
    public String toString() {
        return "\n{\n" +
                "\tid: " + id + "\n" +
                "\tname: " + name + "\n" +
                "\thome: " + home + "\n" +
                "\tquant: " + quant + "\n" +
                "\tprice: " + price + "\n" +
                "\tprice_cntr: " + price_cntr + "\n" +
                "\tpfactor: " + pfactor + "\n" +
                "\tlink: " + link + "\n" +
                "}";
    }

    public PriceItem(String id, String name, String home, Double quant, float price, float price_cntr, int pfactor, Link link) {
        this.id = id;
        this.name = name;
        this.home = home;
        this.quant = quant;
        this.price = price;
        this.price_cntr = price_cntr;
        this.pfactor = pfactor;
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Float getPrice_cntr() {
        return price_cntr;
    }

    public void setPrice_cntr(float price_cntr) {
        this.price_cntr = price_cntr;
    }

    public int getPfactor() {
        return pfactor;
    }

    public void setPfactor(int pfactor) {
        this.pfactor = pfactor;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public PriceItem() {
    }
}
