package pl.kurs.java.AllegroAppl.controller;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.kurs.java.AllegroAppl.model.dto.ProductDTO;
import pl.kurs.java.AllegroAppl.model.entity.User;
import pl.kurs.java.AllegroAppl.service.ProductService;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/allegro")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/name")
    public String getName(Model model) {
        model.addAttribute("formName", new ProductDTO());
        //List<Product> allProducts = productRepository.findAll();
        //model.addAttribute(allProducts);
        //List<Product> productsByCategory = productRepository.findByCategory("user input");
        //allProducts.forEach(System.out::println);
        return "allegroName";
    }

    @PostMapping("/resultName")
    public String showProductsByName(@ModelAttribute ProductDTO product, Model model) {
//        List<Product> result = productRepository.findByName(product.getName());
//        model.addAttribute("result", result);
        return "result";
    }

    @GetMapping("/category")
    public String getCategory(Model model) {
        model.addAttribute("formCategory", new ProductDTO());
        return "allegroCategory";
    }

    @PostMapping("/productList")
    public String showProductsByCategory(@ModelAttribute ProductDTO product, Model model) {
        List<ProductDTO> result = productService.findByCategory(product.getCategory());
        model.addAttribute("result", result);
        return "productList";
    }

    @GetMapping("/product/{id}")
    public String getProductById(@PathVariable int id, Model model) {
        ProductDTO product = productService.findDTOById(id);
        model.addAttribute("product", product);
        return "product";
    }

    @GetMapping("/test")
    @Transactional
    public String test() throws SQLException {
        User currentlyLoggedUser = (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        productService.fillTestData();
        return "result";
    }

   /*
    @GetMapping("/category/{name}")
    public String showProductsByCategory(Model model, @PathVariable String name) {
        List<Product> result = productRepository.findByCategory(name);
        model.addAttribute("result", result);
        return "result";
    }
    */
    //ctrl - to hide commented code
}
