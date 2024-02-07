package kg.kstu.production.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "salary", uniqueConstraints = {@UniqueConstraint(columnNames = {"year", "month", "employee_id"})})
public class Salary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "year")
    private Integer year;

    @Column(name = "month")
    private Integer month;

    @ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = true)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "purchase_id", referencedColumnName = "id", nullable = true)
    private MaterialPurchase purchase;

    @ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "production_id", referencedColumnName = "id", nullable = true)
    private ProductProduction production;

    @ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "sale_id", referencedColumnName = "id", nullable = true)
    private ProductSale sale;

    private Integer common;

    private Float salary;

    private Float bonus;

    private Float general;

    private Boolean status;
}
