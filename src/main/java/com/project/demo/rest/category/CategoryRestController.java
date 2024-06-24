package com.project.demo.rest.category;

import com.project.demo.logic.entity.category.Category;
import com.project.demo.logic.entity.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryRestController {
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public List<Category> getAllCategories(){
        return (List<Category>) categoryRepository.findAll();
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public Category createCategory(@RequestBody Category newCategory){
        return categoryRepository.save(newCategory);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public Category updateCategory(@PathVariable Integer id, @RequestBody Category updatedCategory){
        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    existingCategory.setName(updatedCategory.getName());
                    existingCategory.setDescription(updatedCategory.getDescription());
                    return categoryRepository.save(existingCategory);
                }).orElseGet(() -> {
                    updatedCategory.setId(id);
                    return categoryRepository.save(updatedCategory);
                });
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public void deleteCategory(@PathVariable Integer id){
        categoryRepository.deleteById(id);
    }

}
