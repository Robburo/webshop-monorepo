package webshop.backend.domains.product;

import jakarta.persistence.*;
import lombok.*;
import webshop.backend.domains.category.Category;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private BigDecimal price;
    private int stock;

    @ManyToOne
    private Category category;
}