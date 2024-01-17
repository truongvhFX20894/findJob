package com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.controller;

import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.model.*;
import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.pagination.Pageable;
import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/recruitment")
public class RecruitmentController {
    private RecruitmentService recruitmentService;
    private CategoryService categoryService;
    private CompanyService companyService;
    private UserService userService;
    private ApplyPostService applyPostService;

    @Autowired
    public RecruitmentController(RecruitmentService recruitmentService, CategoryService categoryService, CompanyService companyService, UserService userService, ApplyPostService applyPostService) {
        this.recruitmentService = recruitmentService;
        this.categoryService = categoryService;
        this.companyService = companyService;
        this.userService = userService;
        this.applyPostService = applyPostService;
    }

    @GetMapping("/category/{categoryId}")
    public String showCategoryPage(@PathVariable int categoryId, Model model, HttpSession session,
                                   @RequestParam(value = "page", defaultValue = "1") int page) {
        // truyền thông tin các công ty hàng đầu
        List<Company> companyList = recruitmentService.findTopCompany();
        model.addAttribute("companies", companyList);

        // lấy thông tin việc làm theo phân loại việc làm
        List<Recruitment> recruitmentList = recruitmentService.findByCategoryId(categoryId);

        // phân trang cho danh sách
        Pageable<Recruitment> recruitmentPageable = new Pageable<>(recruitmentList);
        recruitmentPageable.setPage(page);
        model.addAttribute("numberPage", recruitmentPageable.getPage());
        model.addAttribute("listForPage", recruitmentPageable.getListForPage());
        model.addAttribute("list", recruitmentPageable);
        List<Integer> intPage = new ArrayList<>();
        for (int i=1;i<=recruitmentPageable.getMaxPages();i++) {
            intPage.add(i);
        }
        model.addAttribute("maxIntPage", intPage);
        model.addAttribute("keySearch", recruitmentPageable);
        model.addAttribute("categoryId", categoryId);

        return "public/recruitment";
    }

    @GetMapping("/search")
    public String search(@RequestParam("keySearch") String keySearch, Model model, HttpSession session,
                         @RequestParam(value = "page", defaultValue = "1") int page) {
        List<Recruitment> recruitmentList = recruitmentService.findAll();
        List<Recruitment> searchedRecruitments = new ArrayList<>();
        for (Recruitment recruitment: recruitmentList) {
            String text = recruitment.getCategory().getName().toLowerCase();
            if (text.contains(keySearch.toLowerCase())) {
                searchedRecruitments.add(recruitment);
            }
        }
        // phân trang cho danh sách
        Pageable<Recruitment> searchedPageable = new Pageable<>(searchedRecruitments);
        searchedPageable.setPage(page);
        model.addAttribute("numberPage", searchedPageable.getPage());
        model.addAttribute("listForPage", searchedPageable.getListForPage());
        model.addAttribute("list", searchedPageable);
        List<Integer> intPage = new ArrayList<>();
        for (int i=1;i<=searchedPageable.getMaxPages();i++) {
            intPage.add(i);
        }
        model.addAttribute("maxIntPage", intPage);
        model.addAttribute("keySearch", keySearch);

        // truyền lại các thông tin cần thiết
        List<User> userList1 = userService.findUserByRole(2);
        model.addAttribute("numberCandidate", userList1.size());
        List<Company> companyList1 = companyService.findAll();
        model.addAttribute("numberCompany", companyList1.size());
        List<Recruitment> recruitmentList1 = recruitmentService.findAll();
        model.addAttribute("numberRecruitment", recruitmentList1.size());
        return "public/result-search";
    }

    @GetMapping("/searchAddress")
    public String searchAddress(@RequestParam(value = "keySearch") String keySearch, Model model, HttpSession session,
                                @RequestParam(value = "page", defaultValue = "1") int page) {
        List<Recruitment> recruitmentList = recruitmentService.findAll();
        List<Recruitment> searchedRecruitments = new ArrayList<>();
        for (Recruitment recruitment: recruitmentList) {
            String text = recruitment.getAddress().toLowerCase();
            if (text.contains(keySearch.toLowerCase())) {
                searchedRecruitments.add(recruitment);
            }
        }
        // phân trang cho danh sách
        Pageable<Recruitment> searchedPageable = new Pageable<>(searchedRecruitments);
        searchedPageable.setPage(page);
        model.addAttribute("numberPage", searchedPageable.getPage());
        model.addAttribute("listForPage", searchedPageable.getListForPage());
        model.addAttribute("list", searchedPageable);
        List<Integer> intPage = new ArrayList<>();
        for (int i=1;i<=searchedPageable.getMaxPages();i++) {
            intPage.add(i);
        }
        model.addAttribute("maxIntPage", intPage);
        model.addAttribute("keySearch", keySearch);

        // truyền lại các thông tin cần thiết
        List<User> userList1 = userService.findUserByRole(2);
        model.addAttribute("numberCandidate", userList1.size());
        List<Company> companyList1 = companyService.findAll();
        model.addAttribute("numberCompany", companyList1.size());
        List<Recruitment> recruitmentList1 = recruitmentService.findAll();
        model.addAttribute("numberRecruitment", recruitmentList1.size());
        return "public/result-search-address";
    }

    @GetMapping("/post")
    public String postNewRecruitment(Model model, HttpSession session) {
        // Truyền danh sách danh mục công việc để đăng tuyển
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categories", categoryList);

        // truyền id của công ty để đăng tuyển trên id của user
        User user = (User) session.getAttribute("user");
        int userId = user.getId();
        Company company = companyService.findByUserId(userId);
        model.addAttribute("companyID", company.getId());
        return "/public/post-job";
    }

    @PostMapping("/addNewRecruitment")
    public String addNewRecruitment(@ModelAttribute("newRecruitment") Recruitment newRecruitment,
                                    @RequestParam("company_id") int companyId, @RequestParam("category_id") int categoryId,
                                    HttpServletRequest request, RedirectAttributes attributes) {
        Company tempCompany = companyService.findById(companyId);
        newRecruitment.setCompany(tempCompany);
        Category tempCategory = categoryService.findById(categoryId);
        newRecruitment.setCategory(tempCategory);
        newRecruitment.setStatus(0);
        recruitmentService.save(newRecruitment);

        // Cập nhật lại số danh mục bài đăng
        int i = tempCategory.getNumberChoose();
        i = i+1;
        tempCategory.setNumberChoose(i);
        categoryService.save(tempCategory);

        attributes.addFlashAttribute("success", "success");
        String referer = request.getHeader("Referer");
        return "redirect:"+referer;
    }

    @GetMapping("/detail/{recruitmentId}")
    public String showDetailRecruitmentPage(@PathVariable int recruitmentId, Model model, HttpSession session) {
        Recruitment recruitment = recruitmentService.findById(recruitmentId);
        model.addAttribute("recruitment", recruitment);

        // truyền thông tin về những cv đã apply công việc nếu user sử dụng là nhà tuyển dụng
        User user = (User) session.getAttribute("user");
        if (user.getRole().getId()==1) {
            List<ApplyPost> applyPostList = applyPostService.findByRecruitmentId(recruitmentId);
            model.addAttribute("applyPosts", applyPostList);
        }

        // hiển thị 1 số việc làm tương tự (giới hạn 3 công việc)
        List<Recruitment> related = recruitmentService.findRelatedRecruitment(recruitment.getCategory().getId());
        model.addAttribute("listRelated", related);
        return "public/detail-post";
    }

    @GetMapping("/editpost/{recruitmentId}")
    public String editJobPage(@PathVariable int recruitmentId, Model model) {
        Recruitment recruitment = recruitmentService.findById(recruitmentId);
        model.addAttribute("recruitment", recruitment);
        return "public/edit-job";
    }

    @PostMapping("/edit")
    public String editJob(HttpServletRequest request, RedirectAttributes attributes,
                          @ModelAttribute("updateRecruitment") Recruitment newRecruitment,
                          @RequestParam("category_id") int categoryId) {
        Category tempCategory = categoryService.findById(categoryId);
        newRecruitment.setCategory(tempCategory);
        Recruitment oldRecruitment = recruitmentService.findById(newRecruitment.getId());
        // update dữ liệu
        recruitmentService.update(oldRecruitment, newRecruitment);

        attributes.addFlashAttribute("success", "success");
        String referer = request.getHeader("Referer");
        return "redirect:"+referer;
    }

    @PostMapping("/delete")
    public String deleteRecruitment(@RequestParam("recruitment_id") int id, HttpServletRequest request) {
        // Cập nhật lại số danh mục bài đăng
        Recruitment tempRecruitment = recruitmentService.findById(id);
        Category tempCategory = tempRecruitment.getCategory();
        int i = tempCategory.getNumberChoose();
        i = i - 1;
        tempCategory.setNumberChoose(i);
        categoryService.save(tempCategory);

        recruitmentService.deleteById(id);

        String referer = request.getHeader("Referer");
        return "redirect:"+referer;
    }
}
