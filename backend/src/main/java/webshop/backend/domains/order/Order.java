package webshop.backend.domains.order;

import jakarta.persistence.*;
import lombok.*;
import webshop.backend.domains.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private LocalDateTime createdAt;

    private String status; // e.g. PENDING, PAID, SHIPPED

    // Delivery information
    private String recipientName;
    private String street;
    private String postalCode;
    private String city;
    private String country;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();
}