package classes.adapter;

public class RequestPrepsInShop {
    private String id_shop;
    private String id_drug;

    public RequestPrepsInShop() {
    }

    public String getId_shop() {
        return id_shop;
    }

    public void setId_shop(String id_shop) {
        this.id_shop = id_shop;
    }

    public String getId_drug() {
        return id_drug;
    }

    public void setId_drug(String id_drug) {
        this.id_drug = id_drug;
    }

    public RequestPrepsInShop(String id_shop, String id_drug) {
        this.id_shop = id_shop;
        this.id_drug = id_drug;
    }
}
