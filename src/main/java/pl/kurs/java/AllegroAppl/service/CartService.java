package pl.kurs.java.AllegroAppl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.kurs.java.AllegroAppl.exception.PaymentError;
import pl.kurs.java.AllegroAppl.exception.UserNotFound;
import pl.kurs.java.AllegroAppl.model.dto.*;
import pl.kurs.java.AllegroAppl.model.entity.Cart;
import pl.kurs.java.AllegroAppl.model.entity.Product;
import pl.kurs.java.AllegroAppl.model.entity.User;
import pl.kurs.java.AllegroAppl.repository.CartRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductService productService;
    private final CartRepository cartRepository;
    private final RestTemplate restTemplate;

    public void addProductToCart(int productId) {
        Cart cartByCurrentUser = getCartByCurrentUser();
        cartByCurrentUser.addProduct(productService.findById(productId));
        cartRepository.save(cartByCurrentUser);
    }

    private Cart getCartByCurrentUser() {
        User user1 = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user1.getActualCart();
    }

    public CartDTO getCartDtoByCurrentUser() {
        return new CartDTO(getCartByCurrentUser());
    }

    public void payForProductsInCart(CartDTO cart) throws PaymentError, UserNotFound {
        int amount = calculateTotalAmountInCart(getCartByCurrentUser());
        String username = cart.getUser().getUsername();
        TokenCreateRequest tokenCreateRequest = new TokenCreateRequest(username);

        // send a request to TokenApp to get a token for the user
        ResponseEntity<String> tokenResponse = restTemplate.postForEntity("http://localhost:8082/getToken", tokenCreateRequest, String.class);

        if (tokenResponse.getStatusCode().is2xxSuccessful()) {
            // extract the token value from the response body
            String token = tokenResponse.getBody();

            // create the PaymentDTO object with the retrieved token
            PaymentDTO paymentDTO = new PaymentDTO(amount, username, token);
            HttpEntity httpEntity = new HttpEntity(paymentDTO);

            // send the payment request to Bank
            ResponseEntity<String> paymentResponse = restTemplate.exchange("http://localhost:8081/users/pay", HttpMethod.POST, httpEntity, String.class);
            if (paymentResponse.getStatusCode().is2xxSuccessful()) {
                System.out.println("Payment successful");
            } else {
                throw new PaymentError();
            }
        } else {
            throw new UserNotFound();
        }
    }

    private int calculateTotalAmountInCart(Cart cart) {
        Set<Product> products = cart.getProducts();
        return products.stream()
                .mapToInt(Product::getPrice)
                .sum();
    }
}
