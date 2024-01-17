package com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.service;

import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.model.ApplyPost;
import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.model.Category;
import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.repository.ApplyPostRepository;
import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplyPostServiceImpl implements ApplyPostService {
    private ApplyPostRepository applyPostRepository;

    @Autowired
    public ApplyPostServiceImpl(ApplyPostRepository applyPostRepository) {
        this.applyPostRepository = applyPostRepository;
    }

    @Override
    public List<ApplyPost> findAll() {
        return applyPostRepository.findAll();
    }

    @Override
    public ApplyPost findById(int id) {
        Optional<ApplyPost> result = applyPostRepository.findById(id);
        ApplyPost applyPost = null;
        if (result.isPresent()) {
            applyPost = result.get();
        }
        return applyPost;
    }

    @Transactional
    @Override
    public void save(ApplyPost applyPost) {
        applyPostRepository.save(applyPost);
    }

    @Transactional
    @Override
    public void deleteById(int id) {
        applyPostRepository.deleteById(id);
    }

    @Override
    public ApplyPost findByRecruitmentIdAndUserId(int recruitmentId, int userId) {
        return applyPostRepository.findApplyPostByRecruitmentIdAndUserId(recruitmentId, userId);
    }

    @Override
    public List<ApplyPost> findByUserId(int userId) {
        return applyPostRepository.findApplyPostByUserId(userId);
    }

    @Override
    public List<ApplyPost> findByRecruitmentId(int recruitmentId) {
        return applyPostRepository.findApplyPostByRecruitmentId(recruitmentId);
    }
}
