package com.project.demo.rest.product;

import com.project.demo.logic.entity.product.Product;
import com.project.demo.logic.entity.category.Category;
import com.project.demo.logic.entity.category.CategoryRepository;
import com.project.demo.logic.entity.product.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductRestController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired CategoryRepository categoryRepository;

    @GetMapping
    public List<Product> getAllProducts(){
        return (List<Product>) productRepository.findAll();
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public Product createProduct(@RequestBody Product newProduct){
        Optional<Category> existingCategory = categoryRepository.findById(newProduct.getCategory().getId());
        existingCategory.ifPresentOrElse(newProduct::setCategory, () ->{
            newProduct.setCategory(categoryRepository.save(newProduct.getCategory()));
        });

        return productRepository.save(newProduct);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public Product updateProduct(@PathVariable Integer id, @RequestBody Product updatedProduct){
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        Optional<Category> existingCategory = categoryRepository.findById(updatedProduct.getCategory().getId());
        existingCategory.ifPresentOrElse(updatedProduct::setCategory, () ->{
            updatedProduct.setCategory(categoryRepository.save(updatedProduct.getCategory()));
        });
        updatedProduct.setId(existingProduct.getId());
        return productRepository.save(updatedProduct);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public void deleteProduct(@PathVariable Integer id){
        productRepository.deleteById(id);
    }

}
