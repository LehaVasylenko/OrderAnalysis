package classes.order;

import classes.adapter.PrepsInShop;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sftpreader.shopPrice.PriceItem;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class Datum {
    private String id;
    private String ext_id;
    private Double quant;
    private Double price;
    private PrepsInShop api;
    private List<PriceItem> sftp;
    private Long priceTime;
    private String drugInfo;

    public String getNameByIdInSftp(String id) {
        try {
            for (PriceItem item : this.sftp) {
                if (item.getId().equals(id)) {
                    return item.getName();
                }
            }
        } catch (NullPointerException ignored) {}
        return "No Such prep";
    }

    public Datum(String id, String ext_id, double quant, double price) {
        this.id = id;
        this.ext_id = ext_id;
        this.quant = quant;
        this.price = price;
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
