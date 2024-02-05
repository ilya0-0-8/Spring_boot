package com.trueman.KP_Vacancy.services;

import com.trueman.KP_Vacancy.models.Product;
import com.trueman.KP_Vacancy.models.User;
import com.trueman.KP_Vacancy.repositories.ProductRepository;
import com.trueman.KP_Vacancy.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

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

    public Product createProduct(Product product, Long id) {
        User user = userRepository.findById(id).orElse(null);
        product.setUser(user);
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
        productRepository.deleteById(id);
    }
}
