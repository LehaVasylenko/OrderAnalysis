package classes.shops;

import java.util.Objects;

public class ShowShops {
        private String id;
        private String id_ex;
        private String id_orgn;
        private String id_head;
        private String id_corp;
        private String name;
        private String name_ua;
        private String mark;
        private String mark_ua;
        private String corp;
        private String corp_ua;
        private String logo;
        private int rmbr;
        private int bkng;
        private String addr_area;
        private String addr_area_ua;
        private String addr_city;
        private String addr_city_ua;
        private String addr_street;
        private String addr_street_ua;
        private String sale_phone;
        private double geo_lat;
        private double geo_lng;
        private String open_hours;
        private String ax_date;
        private String up_date;
        private String tl_city;

    public ShowShops(String id, String id_ex, String id_orgn, String id_head, String id_corp, String name, String name_ua, String mark, String mark_ua, String corp, String corp_ua, String logo, int rmbr, int bkng, String addr_area, String addr_area_ua, String addr_city, String addr_city_ua, String addr_street, String addr_street_ua, String sale_phone, double geo_lat, double geo_lng, String open_hours, String ax_date, String up_date, String tl_city) {
        this.id = id;
        this.id_ex = id_ex;
        this.id_orgn = id_orgn;
        this.id_head = id_head;
        this.id_corp = id_corp;
        this.name = name;
        this.name_ua = name_ua;
        this.mark = mark;
        this.mark_ua = mark_ua;
        this.corp = corp;
        this.corp_ua = corp_ua;
        this.logo = logo;
        this.rmbr = rmbr;
        this.bkng = bkng;
        this.addr_area = addr_area;
        this.addr_area_ua = addr_area_ua;
        this.addr_city = addr_city;
        this.addr_city_ua = addr_city_ua;
        this.addr_street = addr_street;
        this.addr_street_ua = addr_street_ua;
        this.sale_phone = sale_phone;
        this.geo_lat = geo_lat;
        this.geo_lng = geo_lng;
        this.open_hours = open_hours;
        this.ax_date = ax_date;
        this.up_date = up_date;
        this.tl_city = tl_city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_ex() {
        return id_ex;
    }

    public void setId_ex(String id_ex) {
        this.id_ex = id_ex;
    }

    public String getId_orgn() {
        return id_orgn;
    }

    public void setId_orgn(String id_orgn) {
        this.id_orgn = id_orgn;
    }

    public String getId_head() {
        return id_head;
    }

    public void setId_head(String id_head) {
        this.id_head = id_head;
    }

    public String getId_corp() {
        return id_corp;
    }

    public void setId_corp(String id_corp) {
        this.id_corp = id_corp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_ua() {
        return name_ua;
    }

    public void setName_ua(String name_ua) {
        this.name_ua = name_ua;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getMark_ua() {
        return mark_ua;
    }

    public void setMark_ua(String mark_ua) {
        this.mark_ua = mark_ua;
    }

    public String getCorp() {
        return corp;
    }

    public void setCorp(String corp) {
        this.corp = corp;
    }

    public String getCorp_ua() {
        return corp_ua;
    }

    public void setCorp_ua(String corp_ua) {
        this.corp_ua = corp_ua;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getRmbr() {
        return rmbr;
    }

    public void setRmbr(int rmbr) {
        this.rmbr = rmbr;
    }

    public int getBkng() {
        return bkng;
    }

    public void setBkng(int bkng) {
        this.bkng = bkng;
    }

    public String getAddr_area() {
        return addr_area;
    }

    public void setAddr_area(String addr_area) {
        this.addr_area = addr_area;
    }

    public String getAddr_area_ua() {
        return addr_area_ua;
    }

    public void setAddr_area_ua(String addr_area_ua) {
        this.addr_area_ua = addr_area_ua;
    }

    public String getAddr_city() {
        return addr_city;
    }

    public void setAddr_city(String addr_city) {
        this.addr_city = addr_city;
    }

    public String getAddr_city_ua() {
        return addr_city_ua;
    }

    public void setAddr_city_ua(String addr_city_ua) {
        this.addr_city_ua = addr_city_ua;
    }

    public String getAddr_street() {
        return addr_street;
    }

    public void setAddr_street(String addr_street) {
        this.addr_street = addr_street;
    }

    public String getAddr_street_ua() {
        return addr_street_ua;
    }

    public void setAddr_street_ua(String addr_street_ua) {
        this.addr_street_ua = addr_street_ua;
    }

    public String getSale_phone() {
        return sale_phone;
    }

    public void setSale_phone(String sale_phone) {
        this.sale_phone = sale_phone;
    }

    public double getGeo_lat() {
        return geo_lat;
    }

    public void setGeo_lat(double geo_lat) {
        this.geo_lat = geo_lat;
    }

    public double getGeo_lng() {
        return geo_lng;
    }

    public void setGeo_lng(double geo_lng) {
        this.geo_lng = geo_lng;
    }

    public String getOpen_hours() {
        return open_hours;
    }

    public void setOpen_hours(String open_hours) {
        this.open_hours = open_hours;
    }

    public String getAx_date() {
        return ax_date;
    }

    public void setAx_date(String ax_date) {
        this.ax_date = ax_date;
    }

    public String getUp_date() {
        return up_date;
    }

    public void setUp_date(String up_date) {
        this.up_date = up_date;
    }

    public String getTl_city() {
        return tl_city;
    }

    public void setTl_city(String tl_city) {
        this.tl_city = tl_city;
    }

    public boolean isWorkingTime(long timestamp) {

        return true;
    }

    @Override
    public String toString() {
        return "{\n" +
                "\tid: " + id + "\n" +
                "\tid_ex: " + id_ex + "\n" +
                "\tid_orgn: " + id_orgn + "\n" +
                "\tid_head: " + id_head + "\n" +
                "\tid_corp: " + id_corp + "\n" +
                "\tname: " + name + "\n" +
                "\tname_ua: " + name_ua + "\n" +
                "\tmark: " + mark + "\n" +
                "\tmark_ua: " + mark_ua + "\n" +
                "\tcorp: " + corp + "\n" +
                "\tcorp_ua: " + corp_ua + "\n" +
                "\tlogo: " + logo + "\n" +
                "\trmbr: " + rmbr + "\n" +
                "\tbkng: " + bkng + "\n" +
                "\taddr_area: " + addr_area + "\n" +
                "\taddr_area_ua: " + addr_area_ua + "\n" +
                "\taddr_city: " + addr_city + "\n" +
                "\taddr_city_ua: " + addr_city_ua + "\n" +
                "\taddr_street: " + addr_street + "\n" +
                "\taddr_street_ua: " + addr_street_ua + "\n" +
                "\tsale_phone: " + sale_phone + "\n" +
                "\tgeo_lat: " + geo_lat + "\n" +
                "\tgeo_lng: " + geo_lng + "\n" +
                "\topen_hours: " + open_hours + "\n" +
                "\tax_date: " + ax_date + "\n" +
                "\tup_date: " + up_date + "\n" +
                "\ttl_city: " + tl_city + "\n" +
                "}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShowShops)) return false;
        ShowShops showShops = (ShowShops) o;
        return Objects.equals(getId_corp(), showShops.getId_corp()) && Objects.equals(getMark(), showShops.getMark()) && Objects.equals(getMark_ua(), showShops.getMark_ua()) && Objects.equals(getCorp(), showShops.getCorp()) && Objects.equals(getCorp_ua(), showShops.getCorp_ua()) && Objects.equals(getAddr_area(), showShops.getAddr_area()) && Objects.equals(getAddr_area_ua(), showShops.getAddr_area_ua()) && Objects.equals(getAddr_city(), showShops.getAddr_city()) && Objects.equals(getAddr_city_ua(), showShops.getAddr_city_ua()) && Objects.equals(getAddr_street(), showShops.getAddr_street()) && Objects.equals(getAddr_street_ua(), showShops.getAddr_street_ua());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId_corp(), getMark(), getMark_ua(), getCorp(), getCorp_ua(), getAddr_area(), getAddr_area_ua(), getAddr_city(), getAddr_city_ua(), getAddr_street(), getAddr_street_ua());
    }

    public ShowShops() {
    }
}
