package com.veersablog.BlogApp.controllers;

import com.veersablog.BlogApp.payloads.ApiResponse;
import com.veersablog.BlogApp.payloads.CategoryDto;
import com.veersablog.BlogApp.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/* This is controller for category*/
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid CategoryDto categoryDto) {
        CategoryDto categoryDto1 =
                this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<CategoryDto>(categoryDto1 , HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody @Valid CategoryDto categoryDto , @PathVariable Integer categoryId) {
        CategoryDto categoryDto1 = this.categoryService.updateCategory(categoryDto , categoryId);
        return new ResponseEntity<CategoryDto>(categoryDto1 , HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer categoryId) {
       return new ResponseEntity<CategoryDto>(this.categoryService.getCategoryById(categoryId), HttpStatus.OK );
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return new ResponseEntity<List<CategoryDto>>(this.categoryService.getAllCategories(), HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId) {
      this.categoryService.deleteCategory(categoryId);
      return new ResponseEntity<ApiResponse>(new ApiResponse("Category delete successfully" , true) , HttpStatus.OK);
    }

}
