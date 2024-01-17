package com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.service;

import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.model.CV;
import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.repository.CvRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CvServiceImpl implements CvService {
    private CvRepository cvRepository;

    @Autowired
    public CvServiceImpl(CvRepository cvRepository) {
        this.cvRepository = cvRepository;
    }

    @Override
    public List<CV> findAll() {
        return cvRepository.findAll();
    }

    @Override
    public CV findById(int id) {
        Optional<CV> result = cvRepository.findById(id);
        CV cv = null;
        if (result.isPresent()) {
            cv = result.get();
        }
        return cv;
    }

    @Transactional
    @Override
    public void save(CV cv) {
        cvRepository.save(cv);
    }

    @Transactional
    @Override
    public void deleteById(int id) {
        cvRepository.deleteById(id);
    }

    @Override
    public CV findByUserId(int userId) {
        return cvRepository.findCVByUserId(userId);
    }
}
