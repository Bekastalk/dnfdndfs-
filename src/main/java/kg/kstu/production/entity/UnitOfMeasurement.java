package kg.kstu.production.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "units_of_measurement")
public class UnitOfMeasurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "unitOfMeasurement")
    private List<Material> materials;

    @OneToMany(mappedBy = "unitOfMeasurement")
    private List<Product> products;
}
