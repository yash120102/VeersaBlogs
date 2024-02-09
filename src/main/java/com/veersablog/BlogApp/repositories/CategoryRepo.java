package com.veersablog.BlogApp.repositories;

import com.veersablog.BlogApp.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category , Integer> {
}
