package com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.service;

import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.model.Category;
import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.model.Company;

import java.util.List;

public interface CompanyService {
    public List<Company> findAll();
    public Company findById(int id);
    public void save(Company company);
    public void deleteById(int id);
    public Company findByUserId(int id);
}
