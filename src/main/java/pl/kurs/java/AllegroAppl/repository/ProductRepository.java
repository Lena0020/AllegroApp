package pl.kurs.java.AllegroAppl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.java.AllegroAppl.model.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategory(String category);

    // List<Product> findByName(String name);
}
