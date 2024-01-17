package com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.service;

import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.model.Category;
import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.model.Company;
import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.model.Recruitment;
import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.repository.RecruitmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecruitmentServiceImpl implements RecruitmentService {
    private RecruitmentRepository recruitmentRepository;

    @Autowired
    public RecruitmentServiceImpl(RecruitmentRepository recruitmentRepository) {
        this.recruitmentRepository = recruitmentRepository;
    }

    @Override
    public List<Recruitment> findAll() {
        return recruitmentRepository.findAll();
    }

    @Override
    public Recruitment findById(int id) {
        Optional<Recruitment> result = recruitmentRepository.findById(id);
        Recruitment recruitment = null;
        if (result.isPresent()) {
            recruitment = result.get();
        }
        return recruitment;
    }

    @Transactional
    @Override
    public void save(Recruitment recruitment) {
        recruitmentRepository.save(recruitment);
    }

    @Transactional
    @Override
    public void deleteById(int id) {
        recruitmentRepository.deleteById(id);
    }

    @Override
    public List<Recruitment> findTopRecruitment() {
        return recruitmentRepository.findTopRecruitment();
    }

    @Override
    public void update(Recruitment oldEntity, Recruitment newEntity) {
        oldEntity.setTitle(newEntity.getTitle());
        oldEntity.setDescription(newEntity.getDescription());
        oldEntity.setExperience(newEntity.getExperience());
        oldEntity.setQuantity(newEntity.getQuantity());
        oldEntity.setAddress(newEntity.getAddress());
        oldEntity.setDeadline(newEntity.getDeadline());
        oldEntity.setSalary(newEntity.getSalary());
        oldEntity.setType(newEntity.getType());
        oldEntity.setCategory(newEntity.getCategory());
        recruitmentRepository.save(oldEntity);
    }

    @Override
    public List<Recruitment> findByCategoryId(int categoryId) {
        return recruitmentRepository.findRecruitmentByCategoryId(categoryId);
    }

    @Override
    public List<Recruitment> findRelatedRecruitment(int categoryId) {
        return recruitmentRepository.findRelatedRecruitment(categoryId);
    }

    @Override
    public List<Company> findTopCompany() {
        return recruitmentRepository.findTopCompany();
    }
}
