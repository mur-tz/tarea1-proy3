package com.project.demo.logic.entity.category;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
    Optional<Category> findByName(Category name);
}
