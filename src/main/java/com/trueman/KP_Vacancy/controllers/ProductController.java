package com.trueman.KP_Vacancy.controllers;

import com.trueman.KP_Vacancy.models.Product;
import com.trueman.KP_Vacancy.repositories.ProductRepository;
import com.trueman.KP_Vacancy.repositories.UserRepository;
import com.trueman.KP_Vacancy.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8092")
@RestController
@RequestMapping("/api_products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    public ProductController(ProductService productService, UserRepository userRepository, ProductRepository productRepository) {
        this.productService = productService;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts()
    {
        try
        {
            List<Product> products = new ArrayList<Product>();
            products = productService.getAllProducts();

            if (products.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(products,HttpStatus.OK);
        }
        catch (Exception exception)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//    @GetMapping("/products_status_ok")
//    public ResponseEntity<List<Product>> getAllProductsStatusOK()
//    {
//        try
//        {
//            List<Product> products_status_ok = new ArrayList<Product>();
//            announcements_status_ok = announcmentService.getAllAnnouncements_Status_OK();
//
//            if (announcements_status_ok.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//
//            return new ResponseEntity<>(announcements_status_ok,HttpStatus.OK);
//        }
//        catch (Exception exception)
//        {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @GetMapping("/my_products/{userId}")
    public ResponseEntity<List<Product>> getAllMy_products(@PathVariable("userId") Long userId)
    {

        List<Product> product = new ArrayList<Product>();
        product = productRepository.findByUserId(userId);

        return new ResponseEntity<>(product,HttpStatus.OK);

    }

    @PostMapping("/addProducts/{id}")
    public ResponseEntity<String> createProductUser(@RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("price") int price, @PathVariable("id") Long id, @RequestParam MultipartFile image) throws IOException {
        Product createdProduct = productService.createProduct(name,description, price, id, image);
        return ResponseEntity.ok("Объявление успешно создано!");
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @PutMapping("/moder/{id}")
//    public ResponseEntity<String> moderData(@PathVariable("id") Long id)
//    {
//        Product announcement = announcementRepository.findAnnouncementById(id);
//
//        announcement.setContract_status("Опубликовано!");
//        announcementRepository.save(announcement);
//
//        return ResponseEntity.ok("Объявление успешно модерировано!");
//    }
//    @PutMapping("/block/{id}")
//    public ResponseEntity<String> blockData(@PathVariable("id") Long id)
//    {
//        Product announcement = announcementRepository.findAnnouncementById(id);
//
//        announcement.setContract_status("Заблокировано!");
//        announcementRepository.save(announcement);
//
//        return ResponseEntity.ok("Объявление успешно заблокировано!");
//    }
    //@PostMapping("/AnnouncementsCheck_ok")
    //public ResponseEntity<String> checkAnnouncementOk(@RequestBody Announcement announcement)
    //{
      //  Announcement createdAnnouncement = announcmentService.createAnnouncement(announcement);
       // return ResponseEntity.ok("Объявление успешно создано!");
   // }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        if (updatedProduct != null) {
            return ResponseEntity.ok("Данные о товаре успешно обновлены!");
        } else {
            return ResponseEntity.ok("Данные о товаре не найдены!");
        }
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable("productId") Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok("Товар успешно удален!");
    }
}
