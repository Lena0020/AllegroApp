package pl.kurs.java.AllegroAppl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.java.AllegroAppl.model.entity.Cart;
import pl.kurs.java.AllegroAppl.model.entity.Product;

public interface CartRepository extends JpaRepository<Cart, Integer> {
}