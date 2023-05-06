package pl.kurs.java.AllegroAppl.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kurs.java.AllegroAppl.model.entity.Cart;
import pl.kurs.java.AllegroAppl.model.entity.User;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private int id;
    private String name;
    private String username;
    private String password;
    private Set<Cart> carts;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.carts = user.getCarts();
    }
}
