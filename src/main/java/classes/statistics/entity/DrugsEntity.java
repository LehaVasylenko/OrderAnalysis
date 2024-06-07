package classes.statistics.entity;

import classes.adapter.PrepsInShop;
import jakarta.persistence.*;
import lombok.*;
import sftpreader.shopPrice.PriceItem;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "drugs_in_order", schema = "public")
public class DrugsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dio_id")
    private Long id;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "datum_id", nullable = false)
    private DatumEntity datum;

    @Column(name = "drug_id")
    private String drugId;

    @Column(name = "drug_id_ext")
    private String drugIdExt;

    @Column(name = "quant")
    private String quant;

    @Column(name = "price")
    private String price;
}
