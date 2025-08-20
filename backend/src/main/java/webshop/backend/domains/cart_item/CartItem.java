package webshop.backend.domains.cart_item;

import jakarta.persistence.*;
import lombok.*;
import webshop.backend.domains.product.Product;
import webshop.backend.domains.user.User;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Product product;

    private int quantity;
}