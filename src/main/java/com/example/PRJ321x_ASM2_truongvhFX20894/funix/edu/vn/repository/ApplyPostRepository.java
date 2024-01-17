package com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.repository;

import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.model.ApplyPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplyPostRepository extends JpaRepository<ApplyPost, Integer> {
    @Query("SELECT a FROM ApplyPost a WHERE a.recruitment.id = ?1 AND a.user.id = ?2")
    public ApplyPost findApplyPostByRecruitmentIdAndUserId(int recruitmentId, int userId);

    public List<ApplyPost> findApplyPostByUserId(int userId);

    public List<ApplyPost> findApplyPostByRecruitmentId(int recruitmentId);
}
