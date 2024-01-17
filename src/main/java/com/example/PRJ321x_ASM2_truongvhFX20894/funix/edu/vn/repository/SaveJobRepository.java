package com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.repository;

import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.model.SaveJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaveJobRepository extends JpaRepository<SaveJob, Integer> {
    @Query("SELECT s FROM SaveJob s WHERE s.user.id = ?1 AND s.recruitment.id = ?2")
    public SaveJob findSaveJobByUserIdAndRecruitmentId(int userId, int recruitmentId);

    @Query(value = "SELECT s FROM SaveJob s WHERE s.user.id = ?1")
    public List<SaveJob> findSaveJobByUserId(int userId);
}
