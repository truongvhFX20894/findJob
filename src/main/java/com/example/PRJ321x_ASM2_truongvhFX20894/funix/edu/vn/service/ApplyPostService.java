package com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.service;

import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.model.ApplyPost;
import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.model.Category;

import java.util.List;

public interface ApplyPostService {
    public List<ApplyPost> findAll();
    public ApplyPost findById(int id);
    public void save(ApplyPost applyPost);
    public void deleteById(int id);
    public ApplyPost findByRecruitmentIdAndUserId(int recruitmentId, int userId);
    public List<ApplyPost> findByUserId(int userId);
    public List<ApplyPost> findByRecruitmentId(int recruitmentId);
}
