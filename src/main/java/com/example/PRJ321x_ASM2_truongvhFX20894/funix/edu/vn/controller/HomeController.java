package com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.controller;

import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.model.*;
import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    private CategoryService categoryService;
    private RoleService roleService;
    private CompanyService companyService;
    private RecruitmentService recruitmentService;
    private UserService userService;
    private SaveJobService saveJobService;
    private CvService cvService;

    @Autowired
    public HomeController(CategoryService categoryService, RoleService roleService, CompanyService companyService, RecruitmentService recruitmentService, UserService userService, SaveJobService saveJobService, CvService cvService) {
        this.categoryService = categoryService;
        this.roleService = roleService;
        this.companyService = companyService;
        this.recruitmentService = recruitmentService;
        this.userService = userService;
        this.saveJobService = saveJobService;
        this.cvService = cvService;
    }

    @GetMapping("/home")
    public String homePage(Model model) {
        List<Category> categoryList = categoryService.findTop4Category();
        model.addAttribute("categories", categoryList); // truyền thông tin các công việc hàng đầu

        List<Company> companyList = recruitmentService.findTopCompany();
        model.addAttribute("companies", companyList); // truyền thông tin các công ty hàng đầu

        List<Recruitment> recruitmentList = recruitmentService.findTopRecruitment();
        model.addAttribute("recruitments", recruitmentList); // truyền thông tin các bài đăng việc làm hàng đầu (mới nhất)

        // Hiển thị số liệu việc làm tổng quan
        List<User> userList1 = userService.findUserByRole(2);
        model.addAttribute("numberCandidate", userList1.size());
        List<Company> companyList1 = companyService.findAll();
        model.addAttribute("numberCompany", companyList1.size());
        List<Recruitment> recruitmentList1 = recruitmentService.findAll();
        model.addAttribute("numberRecruitment", recruitmentList1.size());
        return "public/home";
    }

    @GetMapping("/profile/{userId}")
    public String profilePage(@PathVariable int userId, Model model) {
        User user = userService.findById(userId);

        //truyền thông tin công ty của tài khoản recruitment
        Company company = companyService.findByUserId(userId);
        model.addAttribute("userInformation", user);
        model.addAttribute("companyInformation", company);

        // truyền thông tin CV của tài khoản user
        CV cv = cvService.findByUserId(userId);
        model.addAttribute("Cv", cv);
        return "public/profile";
    }

    @PostMapping("/save-job/{id}")
    @ResponseBody
    public String saveJob(@PathVariable int id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (saveJobService.findByUserIdAndRecruitmentId(user.getId(), id)==null) {
            Recruitment recruitment = recruitmentService.findById(id);
            SaveJob saveJob = new SaveJob();
            saveJob.setUser(user);
            saveJob.setRecruitment(recruitment);
            saveJobService.save(saveJob);
            return "true";
        } else {
            return "false";
        }
    }

    @GetMapping("/list-job")
    public String listJobPage() {
        return "public/listJob";
    }
}
