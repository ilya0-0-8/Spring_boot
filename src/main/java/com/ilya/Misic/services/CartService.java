package com.ilya.Misic.services;

import com.ilya.Misic.models.Cart;
import com.ilya.Misic.models.Product;
import com.ilya.Misic.models.User;
import com.ilya.Misic.repositories.CartRepository;
import com.ilya.Misic.repositories.ProductRepository;
import com.ilya.Misic.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CartService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;

    public CartService(ProductRepository productRepository, UserRepository userRepository, CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.cartRepository= cartRepository;
    }

    public List<Cart> getAllProductsCart() {
        return cartRepository.findAll();
    }

    public Cart getCartById(Long id) {
        return cartRepository.findById(id).orElse(null);
    }

    public Cart createProduct(Long productId, Long userId) throws IOException {

        Product product = productRepository.findById(productId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        Cart cart = new Cart();

        cart.setProduct(product);
        cart.setUser(user);
        cart.setCount(1);

        Integer currentTotal = cart.getTotal();

        if (currentTotal == null) {
            currentTotal = 0;
        }
        int newPrice = product.getPrice();
        int updatedTotal = currentTotal + newPrice;

        cart.setTotal(updatedTotal);

        Cart savedCart= cartRepository.save(cart);

        return savedCart;
    }
    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }
}
