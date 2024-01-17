package com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.service;

import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.model.FollowCompany;
import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.repository.FollowCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FollowCompanyServiceImpl implements FollowCompanyService {
    private FollowCompanyRepository followCompanyRepository;

    @Autowired
    public FollowCompanyServiceImpl(FollowCompanyRepository followCompanyRepository) {
        this.followCompanyRepository = followCompanyRepository;
    }

    @Override
    public List<FollowCompany> findAll() {
        return followCompanyRepository.findAll();
    }

    @Override
    public FollowCompany findById(int id) {
        Optional<FollowCompany> result = followCompanyRepository.findById(id);
        FollowCompany followCompany = null;
        if (result.isPresent()) {
            followCompany = result.get();
        }
        return followCompany;
    }

    @Override
    public void save(FollowCompany followCompany) {
        followCompanyRepository.save(followCompany);
    }

    @Override
    public void deleteById(int id) {
        followCompanyRepository.deleteById(id);
    }

    @Override
    public FollowCompany findByUserIdAndCompanyId(int userId, int companyId) {
        return followCompanyRepository.findFollowCompanyByUserIdAndCompanyId(userId,companyId);
    }

    @Override
    public List<FollowCompany> findByUserId(int userId) {
        return followCompanyRepository.findFollowCompanyByUserId(userId);
    }
}
