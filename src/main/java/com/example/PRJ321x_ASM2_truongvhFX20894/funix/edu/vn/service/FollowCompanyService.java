package com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.service;

import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.model.FollowCompany;
import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.model.SaveJob;

import java.util.List;

public interface FollowCompanyService {
    public List<FollowCompany> findAll();
    public FollowCompany findById(int id);
    public void save(FollowCompany followCompany);
    public void deleteById(int id);
    public FollowCompany findByUserIdAndCompanyId(int userId, int companyId);
    public List<FollowCompany> findByUserId(int userId);
}
