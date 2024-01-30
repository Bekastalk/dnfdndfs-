package kg.kstu.production.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "unitOfMeasurement_id", referencedColumnName = "id", nullable = true)
    private UnitOfMeasurement unitOfMeasurement;

    @Column(name = "quantity")
    private Float quantity;

    @Column(name = "amount")
    private Float amount;
}
