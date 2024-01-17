package com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.service;

import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.model.SaveJob;

import java.util.List;

public interface SaveJobService {
    public List<SaveJob> findAll();
    public SaveJob findById(int id);
    public void save(SaveJob saveJob);
    public void deleteById(int id);
    public SaveJob findByUserIdAndRecruitmentId(int userId, int recruitmentId);
    public List<SaveJob> findByUserId(int userId);
}
