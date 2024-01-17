package com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.repository;

import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.model.CV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CvRepository extends JpaRepository<CV, Integer> {
    public CV findCVByUserId(int userId);
}
