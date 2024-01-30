package kg.kstu.production.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "position_id", referencedColumnName = "id", nullable = true)
    private Position position;

    @Column(name = "salary")
    private Float salary;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phone_number;

    @CreationTimestamp
    private LocalDateTime cts;

    @UpdateTimestamp
    private LocalDateTime uts;
}
