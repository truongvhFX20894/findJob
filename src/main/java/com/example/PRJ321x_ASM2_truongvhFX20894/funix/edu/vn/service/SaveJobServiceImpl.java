package com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.service;

import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.model.SaveJob;
import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.model.User;
import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.repository.SaveJobRepository;
import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SaveJobServiceImpl implements SaveJobService {
    private SaveJobRepository saveJobRepository;

    @Autowired
    public SaveJobServiceImpl(SaveJobRepository saveJobRepository) {
        this.saveJobRepository = saveJobRepository;
    }

    @Override
    public List<SaveJob> findAll() {
        return saveJobRepository.findAll();
    }

    @Override
    public SaveJob findById(int id) {
        Optional<SaveJob> result = saveJobRepository.findById(id);
        SaveJob saveJob = null;
        if (result.isPresent()) {
            saveJob = result.get();
        }
        return saveJob;
    }

    @Transactional
    @Override
    public void save(SaveJob saveJob) {
        saveJobRepository.save(saveJob);
    }

    @Transactional
    @Override
    public void deleteById(int id) {
        saveJobRepository.deleteById(id);
    }

    @Override
    public SaveJob findByUserIdAndRecruitmentId(int userId, int recruitmentId) {
        return saveJobRepository.findSaveJobByUserIdAndRecruitmentId(userId, recruitmentId);
    }

    @Override
    public List<SaveJob> findByUserId(int userId) {
        return saveJobRepository.findSaveJobByUserId(userId);
    }
}
