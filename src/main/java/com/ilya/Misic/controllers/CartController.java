package com.ilya.Misic.controllers;

import com.ilya.Misic.models.Cart;
import com.ilya.Misic.repositories.CartRepository;
import com.ilya.Misic.repositories.ProductRepository;
import com.ilya.Misic.repositories.UserRepository;
import com.ilya.Misic.services.CartService;
import com.ilya.Misic.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8092")
@RestController
@RequestMapping("/api_carts")
public class CartController {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartService cartService;
    public CartController(ProductService productService, UserRepository userRepository, ProductRepository productRepository, CartService cartService, CartRepository cartRepository) {
        this.productService = productService;
        this.cartService = cartService;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }
    @GetMapping("/my_products/{userId}")
    public ResponseEntity<List<Cart>> getAllMy_products(@PathVariable("userId") Long userId)
    {

        List<Cart> cart = new ArrayList<Cart>();
        cart = cartRepository.findByUserId(userId);

        return new ResponseEntity<>(cart, HttpStatus.OK);

    }

    @PostMapping("/addProductCart/{productId}/{user_productId}")
    public ResponseEntity<String> createProductCart(@PathVariable("productId") Long productId, @PathVariable("user_productId") Long user_productId) throws IOException {

        List<Cart> existingCarts = cartRepository.findByProductIdAndUserId(productId, user_productId);

        if(existingCarts.isEmpty())
        {
            Cart createdCart = cartService.createProduct(productId, user_productId);
            return ResponseEntity.ok("Объявление успешно добавлено в корзину!");
        }
        else {
            Cart existingCart = existingCarts.get(0);
            existingCart.setCount(existingCart.getCount() + 1);
            existingCart.setTotal(existingCart.getTotal() + existingCart.getProduct().getPrice());

            cartRepository.save(existingCart);
            return ResponseEntity.ok("Количество товара в корзине успешно обновлено!");
        }
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable("productId") Long productId) {
        Cart cart = cartRepository.findProductById(productId);
        if(cart.getCount() > 1)
        {
            cart.setCount(cart.getCount() - 1);
            cart.setTotal(cart.getTotal() - cart.getProduct().getPrice());
            cartRepository.save(cart);
            return ResponseEntity.ok("Количество товаров уменьшено на 1!");
        }
        else {
            cartRepository.deleteById(productId);
            return ResponseEntity.ok("Товар успешно удален из корзины!");
        }
    }
}
