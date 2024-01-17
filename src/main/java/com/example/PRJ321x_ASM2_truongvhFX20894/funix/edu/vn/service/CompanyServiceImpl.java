package com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.service;

import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.model.Company;
import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.model.Role;
import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.repository.CompanyRepository;
import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {
    private CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public Company findById(int id) {
        Optional<Company> result = companyRepository.findById(id);
        Company company = null;
        if (result.isPresent()) {
            company = result.get();
        }
        return company;
    }

    @Transactional
    @Override
    public void save(Company company) {
        companyRepository.save(company);
    }

    @Transactional
    @Override
    public void deleteById(int id) {
        companyRepository.deleteById(id);
    }

    @Override
    public Company findByUserId(int id) {
        List<Company> companyList = companyRepository.findAll();
        for (Company company: companyList) {
            if (company.getUser().getId()==id) {
                return company;
            }
        }
        return null;
    }
}
