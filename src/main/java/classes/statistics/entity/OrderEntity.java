package classes.statistics.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orders", schema = "public")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id")
    private String idOrder;

    @Column(name = "order_id_ext")
    private String idOrderExt;

    @Column(name = "shop_id")
    private String idShop;

    @Column(name = "shop_id_ext")
    private String idShopExt;

    @Column(name = "corp_id")
    private String idCorp;

    @Column(name = "shipping")
    private String shipping;

    @Column(name = "agent")
    private String agent;

    @Column(name = "user_phone")
    private String phone;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DatumEntity> datums;
}
