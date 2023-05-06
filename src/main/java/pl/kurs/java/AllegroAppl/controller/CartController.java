package pl.kurs.java.AllegroAppl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.kurs.java.AllegroAppl.exception.PaymentError;
import pl.kurs.java.AllegroAppl.exception.UserNotFound;
import pl.kurs.java.AllegroAppl.model.dto.CartDTO;
import pl.kurs.java.AllegroAppl.model.dto.PaymentDTO;
import pl.kurs.java.AllegroAppl.model.dto.ProductDTO;
import pl.kurs.java.AllegroAppl.model.entity.User;
import pl.kurs.java.AllegroAppl.service.CartService;
import pl.kurs.java.AllegroAppl.service.ProductService;
import pl.kurs.java.AllegroAppl.service.UserService;

import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/allegro")
public class CartController {
    private final CartService cartService;

    @PostMapping("/add/{productId}")
    public String addToCart(@PathVariable("productId") int productId) {
        cartService.addProductToCart(productId);
        return "productAdded";
    }

    @GetMapping("/cart")
    public String getCartByCurrentUser(Model model) {
        CartDTO cartDTO = cartService.getCartDtoByCurrentUser();
        List<ProductDTO> products = cartDTO.getProducts();
        model.addAttribute("products", products);
        return "cart";
    }
    @PostMapping("/checkout")
    public void checkout() throws PaymentError, UserNotFound {
        CartDTO cartDTO = cartService.getCartDtoByCurrentUser();
        cartService.payForProductsInCart(cartDTO);
    }
}
