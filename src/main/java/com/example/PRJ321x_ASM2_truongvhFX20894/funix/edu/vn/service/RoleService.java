package com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.service;

import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.model.Category;
import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.model.Role;

import java.util.List;

public interface RoleService {
    public List<Role> findAll();
    public Role findById(int id);
    public void save(Role role);
    public void deleteById(int id);
}
