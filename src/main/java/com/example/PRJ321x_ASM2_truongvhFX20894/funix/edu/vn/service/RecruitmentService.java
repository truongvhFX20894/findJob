package com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.service;

import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.model.Category;
import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.model.Company;
import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.model.Recruitment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecruitmentService {
    public List<Recruitment> findAll();
    public Recruitment findById(int id);
    public void save(Recruitment recruitment);
    public void deleteById(int id);
    public List<Recruitment> findTopRecruitment();
    public void update(Recruitment oldEntity, Recruitment newEntity);
    public List<Recruitment> findByCategoryId(int categoryId);
    public List<Recruitment> findRelatedRecruitment(int categoryId);
    public List<Company> findTopCompany();
}
