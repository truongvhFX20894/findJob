package com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.service;

import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.model.CV;
import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.model.Category;

import java.util.List;

public interface CvService {
    public List<CV> findAll();
    public CV findById(int id);
    public void save(CV cv);
    public void deleteById(int id);
    public CV findByUserId(int userId);
}
