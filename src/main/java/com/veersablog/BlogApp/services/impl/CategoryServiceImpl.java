package com.veersablog.BlogApp.services.impl;

import com.veersablog.BlogApp.entities.Category;
import com.veersablog.BlogApp.exceptions.ResourceNotFoundException;
import com.veersablog.BlogApp.payloads.CategoryDto;
import com.veersablog.BlogApp.repositories.CategoryRepo;
import com.veersablog.BlogApp.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepo categoryRepo;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.CategoryDtoToCategory(categoryDto);
        Category savedCategory = this.categoryRepo.save(category);
        return this.CategoryToCategoryDto(savedCategory);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() ->new ResourceNotFoundException("Category" , "categoryId" , categoryId));

        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());

        Category savedCategory = this.categoryRepo.save(category);
        return this.CategoryToCategoryDto(savedCategory);
    }

    @Override
    public void deleteCategory(Integer categoryId) {

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() ->new ResourceNotFoundException("Category" , "categoryId" , categoryId));
        this.categoryRepo.delete(category);

    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() ->new ResourceNotFoundException("Category" , "categoryId" , categoryId));
         return this.CategoryToCategoryDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = this.categoryRepo.findAll();
        List<CategoryDto> categoryDtos = categories.stream().map(this::CategoryToCategoryDto).toList();
        return categoryDtos;
    }

    Category CategoryDtoToCategory(CategoryDto categoryDto) {
        return this.modelMapper.map(categoryDto , Category.class);
    }

    CategoryDto CategoryToCategoryDto(Category category) {
        return this.modelMapper.map(category , CategoryDto.class);
    }
}
