package classes.statistics.entity;

import classes.statistics.mapper.DateUtil;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "datum", schema = "public")
public class DatumEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "datum_id")
    private Long id;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    @Column(name = "date")
    private Date timestamp;

    @Column(name = "state")
    private String state;

    @OneToMany(mappedBy = "datum", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DrugsEntity> drugs;

    public String getFormattedTimestamp() {
        return DateUtil.formatDate(this.timestamp);
    }
}
