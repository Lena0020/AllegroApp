package pl.kurs.java.AllegroAppl.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kurs.java.AllegroAppl.model.dto.UserDTO;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private int id;

    private boolean isPaid;

    @ManyToMany(fetch = FetchType.EAGER)
//    @Cascade(CascadeType.ALL)
    @JoinTable(
            name = "cart_products",
            joinColumns = {@JoinColumn(name = "cart_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id")}
    )
    private Set<Product> products = new HashSet<>();
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public void addProduct(Product product) {
        products.add(product);
    }

    public void payForProducts(Cart cart) {
        cart.setPaid(true);
    }

}
