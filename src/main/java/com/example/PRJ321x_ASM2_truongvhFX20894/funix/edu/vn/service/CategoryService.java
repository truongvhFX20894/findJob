package com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.service;

import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.model.Category;

import java.util.List;

public interface CategoryService {
    public List<Category> findAll();
    public Category findById(int id);
    public void save(Category category);
    public void deleteById(int id);
    public List<Category> findTop4Category();
}
