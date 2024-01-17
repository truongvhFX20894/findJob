package com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.repository;

import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.model.Company;
import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.model.Recruitment;
import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.model.SaveJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecruitmentRepository extends JpaRepository<Recruitment, Integer> {
    @Query(value = "SELECT r FROM Recruitment r ORDER BY r.createAt DESC, r.view DESC LIMIT 5")
    public List<Recruitment> findTopRecruitment();

    @Query(value = "SELECT r FROM Recruitment r WHERE r.category.id = ?1")
    public List<Recruitment> findRecruitmentByCategoryId(int categoryId);

    @Query(value = "SELECT r FROM Recruitment r WHERE r.category.id = ?1 ORDER BY r.createAt DESC LIMIT 3")
    public List<Recruitment> findRelatedRecruitment(int categoryId);

    @Query(value = "SELECT r.company FROM Recruitment r GROUP BY r.company ORDER BY COUNT(r.id) DESC LIMIT 3")
    public List<Company> findTopCompany();
}
