package com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.repository;

import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.model.FollowCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowCompanyRepository extends JpaRepository<FollowCompany, Integer> {
    @Query("SELECT f FROM FollowCompany f WHERE f.user.id = ?1 AND f.company.id = ?2")
    public FollowCompany findFollowCompanyByUserIdAndCompanyId(int userId, int companyId);

    @Query(value = "SELECT f FROM FollowCompany f WHERE f.user.id = ?1")
    public List<FollowCompany> findFollowCompanyByUserId(int userId);
}
