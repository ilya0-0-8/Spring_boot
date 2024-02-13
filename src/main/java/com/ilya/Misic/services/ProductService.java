package com.ilya.Misic.services;

import com.ilya.Misic.models.Product;
import com.ilya.Misic.models.User;
import com.ilya.Misic.repositories.CartRepository;
import com.ilya.Misic.repositories.ProductRepository;
import com.ilya.Misic.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;

    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    //    public List<Product> getAllAnnouncements_Status_OK() {
////        String contract_status = "Модерировано!";
//        String contract_status = "Опубликовано!";
//        return announcementRepository.findAll(contract_status);
//    }
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product createProduct(String name, String description, int price, Long id, MultipartFile image) throws IOException {
        Product product = new Product();
        User user = userRepository.findById(id).orElse(null);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setUser(user);
        product.setImage(image.getBytes());
        return productRepository.save(product);
    }
    public Product updateProduct(Long id, Product product) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct != null) {
            existingProduct.setName(product.getName());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPrice(product.getPrice());
            return productRepository.save(existingProduct);
        }
        return null;
    }
    @Transactional
    public void deleteProduct(Long id) {
        cartRepository.deleteByProductId(id);
        productRepository.deleteById(id);
    }
}
