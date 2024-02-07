package kg.kstu.production.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "product_production")
public class ProductProduction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = true)
    private Product product;

    @Column(name = "quantity")
    private Float quantity;

    @Column(name = "p_date")
    private LocalDateTime pDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = true)
    private Employee employee;
}
