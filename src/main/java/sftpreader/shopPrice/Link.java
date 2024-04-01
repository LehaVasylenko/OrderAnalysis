package sftpreader.shopPrice;

public class Link {
    private String id;
    private String id_link;
    private String id_catg;
    private String id_stat;
    private String id_drug;

    public String getId_stat() {
        return id_stat;
    }

    public void setId_stat(String id_stat) {
        this.id_stat = id_stat;
    }

    public Link(String id, String id_link, String id_catg, String id_stat, String id_drug) {
        this.id = id;
        this.id_link = id_link;
        this.id_catg = id_catg;
        this.id_stat = id_stat;
        this.id_drug = id_drug;
    }

    @Override
    public String toString() {
        return "{\n" +
                "\t\tid: " + id + "\n" +
                "\t\tid_link: " + id_link + "\n" +
                "\t\tid_catg: " + id_catg + "\n" +
                "\t\tid_stat: " + id_stat + "\n" +
                "\t\tid_drug: " + id_drug + "\n" +
                "\t}\n";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_link() {
        return id_link;
    }

    public void setId_link(String id_link) {
        this.id_link = id_link;
    }

    public String getId_catg() {
        return id_catg;
    }

    public void setId_catg(String id_catg) {
        this.id_catg = id_catg;
    }

    public String getId_drug() {
        return id_drug;
    }

    public void setId_drug(String id_drug) {
        this.id_drug = id_drug;
    }

    public Link() {
    }
}
