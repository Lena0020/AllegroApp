package pl.kurs.java.AllegroAppl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.java.AllegroAppl.model.dto.ProductDTO;
import pl.kurs.java.AllegroAppl.model.entity.Cart;
import pl.kurs.java.AllegroAppl.model.entity.Product;
import pl.kurs.java.AllegroAppl.repository.CartRepository;
import pl.kurs.java.AllegroAppl.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public List<ProductDTO> findByCategory(String category) {
        return productRepository.findByCategory(category).stream().map(ProductDTO::new).toList();
    }

    public Product findById(int id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("prodyuct o id " + id + " nie istnieje"));
    }

    public ProductDTO findDTOById(int id) {
        return new ProductDTO(findById(id));
    }

    public void fillTestData() {
        Cart cart = cartRepository.findById(3).orElse(null);
        System.out.println(cart.getProducts());
        Cart c1 = new Cart();
        c1.setPaid(false);
        Product product = productRepository.saveAndFlush(new Product().setCategory("house").setName("table").setPrice(10));
        c1.addProduct(product);
        product = productRepository.saveAndFlush(new Product().setCategory("sweets").setName("candy bar").setPrice(5));
        c1.addProduct(product);
        product = productRepository.saveAndFlush(new Product().setCategory("garden").setName("chair").setPrice(100));
        c1.addProduct(product);
        cartRepository.saveAndFlush(c1);
    }
}
