package pl.kurs.java.AllegroAppl.model.dto;

import lombok.*;
import pl.kurs.java.AllegroAppl.model.entity.Cart;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class CartDTO {
    private int id;
    private boolean isPaid;
    private UserDTO user;
    private List<ProductDTO> products;

    public CartDTO(Cart cart) {
        this.id = cart.getId();
        this.isPaid = cart.isPaid();
        this.user = new UserDTO(cart.getUser());
        this.products = cart.getProducts().stream()
                .map(ProductDTO::new)
                .collect(Collectors.toList());
    }
}
