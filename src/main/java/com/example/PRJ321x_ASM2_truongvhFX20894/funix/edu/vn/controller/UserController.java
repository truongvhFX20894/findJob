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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    private CompanyService companyService;
    private SaveJobService saveJobService;
    private FollowCompanyService followCompanyService;
    private RecruitmentService recruitmentService;
    private CvService cvService;
    private ApplyPostService applyPostService;
    private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));

    @Autowired
    public UserController(UserService userService, CompanyService companyService, SaveJobService saveJobService, FollowCompanyService followCompanyService, RecruitmentService recruitmentService, CvService cvService, ApplyPostService applyPostService) {
        this.userService = userService;
        this.companyService = companyService;
        this.saveJobService = saveJobService;
        this.followCompanyService = followCompanyService;
        this.recruitmentService = recruitmentService;
        this.cvService = cvService;
        this.applyPostService = applyPostService;
    }

    @GetMapping("/search")
    public String search(@RequestParam(value = "keySearch") String keySearch, Model model, HttpSession session,
                         @RequestParam(value = "page", defaultValue = "1") int page) {
        // tìm kiếm việc làm theo tên công ty
        List<Recruitment> recruitmentList = recruitmentService.findAll();
        List<Recruitment> searchedRecruitments = new ArrayList<>();
        for (Recruitment recruitment: recruitmentList) {
            String text = recruitment.getCompany().getNameCompany().toLowerCase();
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
        return "public/result-search-user";
    }

    @GetMapping("/get-list-job")
    public String listJobPage(HttpSession session, Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        // Tìm các công việc đã lưu theo user id
        User user = (User) session.getAttribute("user");
        int userId = user.getId();
        List<SaveJob> saveJobList = saveJobService.findByUserId(userId);

        // phân trang cho danh sách công việc đã lưu
        Pageable<SaveJob> saveJobPageable = new Pageable<>(saveJobList);
        saveJobPageable.setPage(page);
        model.addAttribute("numberPage", saveJobPageable.getPage());
        model.addAttribute("listForPage", saveJobPageable.getListForPage());
        model.addAttribute("list", saveJobPageable);
        List<Integer> intPage = new ArrayList<>();
        for (int i=1;i<=saveJobPageable.getMaxPages();i++) {
            intPage.add(i);
        }
        model.addAttribute("maxIntPage", intPage);
        return "public/list-save-job";
    }

    @GetMapping("/get-list-apply")
    public String applyJobPage(HttpSession session, Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        User user = (User) session.getAttribute("user");
        int id = user.getId();
        List<ApplyPost> applyPosts = applyPostService.findByUserId(id);

        // phân trang cho danh sách công ty đang theo dõi
        Pageable<ApplyPost> applyPostPageable = new Pageable<>(applyPosts);
        applyPostPageable.setPage(page);
        model.addAttribute("numberPage", applyPostPageable.getPage());
        model.addAttribute("listForPage", applyPostPageable.getListForPage());
        model.addAttribute("list", applyPostPageable);
        List<Integer> intPage = new ArrayList<>();
        for (int i=1;i<=applyPostPageable.getMaxPages();i++) {
            intPage.add(i);
        }
        model.addAttribute("maxIntPage", intPage);
        return "public/list-apply-job";
    }

    @GetMapping("/get-list-company")
    public String followCompanyPage(HttpSession session, Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        // Tìm các công ty đã theo dõi theo user id
        User user = (User) session.getAttribute("user");
        int userId = user.getId();
        List<FollowCompany> followCompanyList = followCompanyService.findByUserId(userId);

        // phân trang cho danh sách công ty đang theo dõi
        Pageable<FollowCompany> followCompanyPageable = new Pageable<>(followCompanyList);
        followCompanyPageable.setPage(page);
        model.addAttribute("numberPage", followCompanyPageable.getPage());
        model.addAttribute("listForPage", followCompanyPageable.getListForPage());
        model.addAttribute("list", followCompanyPageable);
        List<Integer> intPage = new ArrayList<>();
        for (int i=1;i<=followCompanyPageable.getMaxPages();i++) {
            intPage.add(i);
        }
        model.addAttribute("maxIntPage", intPage);
        return "public/list-follow-company";
    }

    @GetMapping("/list-post")
    public String listPostPage(HttpSession session, Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        // lấy danh sách việc làm đã đăng dựa trên id của user
        User user = (User) session.getAttribute("user");
        int userId = user.getId();
        Company company = companyService.findByUserId(userId);
        List<Recruitment> recruitmentList = company.getRecruitmentList();

        // phân trang cho danh sách việc làm
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
        return "public/post-list";
    }

    @GetMapping("/detail-company/{companyId}")
    public String showDetailCompany(@PathVariable int companyId, Model model) {
        Company company = companyService.findById(companyId);
        model.addAttribute("company", company);
        return "public/detail-company";
    }

    @PostMapping("/follow-company")
    @ResponseBody
    public String followCompany(HttpSession session, @RequestParam int idCompany) {
        User user = (User) session.getAttribute("user");
        if (followCompanyService.findByUserIdAndCompanyId(user.getId(), idCompany)==null) {
            Company company = companyService.findById(idCompany);
            FollowCompany followCompany = new FollowCompany();
            followCompany.setUser(user);
            followCompany.setCompany(company);
            followCompanyService.save(followCompany);
            return "true"; // trả về true sau khi đã follow công ty mới
        } else {
            return "false"; // trả về false nếu công ty trên đã được follow từ trước
        }
    }

    @GetMapping("/save-job/delete/{saveJobId}")
    public String deleteSaveJob(@PathVariable int saveJobId, HttpServletRequest request, RedirectAttributes attributes) {
        saveJobService.deleteById(saveJobId);
        attributes.addFlashAttribute("success", "deleteSuccess");
        String referer = request.getHeader("Referer");
        return "redirect:"+referer;
    }

    @GetMapping("/delete-follow/{followCompanyId}")
    public String deleteFollowCompany(@PathVariable int followCompanyId, HttpServletRequest request, RedirectAttributes attributes) {
        followCompanyService.deleteById(followCompanyId);
        attributes.addFlashAttribute("success", "deleteSuccess");
        String referer = request.getHeader("Referer");
        return "redirect:"+referer;
    }

    @GetMapping("/delete-apply/{applyId}")
    public String deleteApplyPost(@PathVariable int applyId, HttpServletRequest request, RedirectAttributes attributes) {
        applyPostService.deleteById(applyId);
        attributes.addFlashAttribute("success", "deleteSuccess");
        String referer = request.getHeader("Referer");
        return "redirect:"+referer;
    }

    @GetMapping("/company-post/{companyId}")
    public String showPostOfCompany(@PathVariable int companyId, Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        // truyền thông tin công ty vào trang
        Company company = companyService.findById(companyId);
        model.addAttribute("company", company);

        // lấy danh sách việc làm mà công ty đã đăng
        List<Recruitment> recruitmentList = company.getRecruitmentList();

        // phân trang cho danh sách việc làm
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
        return "public/post-company";
    }

    @PostMapping("/uploadCv")
    @ResponseBody
    public String uploadCV(@RequestParam("file") MultipartFile file, HttpSession session) {
        // tạo đường dẫn cho thư mục lưu file
        Path staticPath = Paths.get("static");
        Path uploadsPath = Paths.get("uploads");
        Path cvPath = Paths.get("cv");
        boolean b = Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(uploadsPath).resolve(cvPath));
        if (!b){
            try {
                Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(uploadsPath).resolve(cvPath));
            } catch (IOException e) {
                return "false";
            }
        }
        // Lưu file vào thư mục
        Path cvFile = CURRENT_FOLDER.resolve(staticPath).resolve(uploadsPath)
                .resolve(cvPath).resolve(file.getOriginalFilename());
        try (OutputStream os = Files.newOutputStream(cvFile)) {
            os.write(file.getBytes());
        } catch (Exception e) {
            return "false";
        }
        // Cập nhật dữ liệu CV mới vào database
        User user = (User) session.getAttribute("user");
        CV updateCV = cvService.findByUserId(user.getId());
        if (updateCV==null) {
            CV newCv = new CV();
            newCv.setCvUrl("\\"+ uploadsPath.resolve(cvPath).resolve(file.getOriginalFilename()));
            newCv.setFileName(file.getOriginalFilename());
            newCv.setUser(user);
            cvService.save(newCv);
            return newCv.getCvUrl();
        } else {
            updateCV.setCvUrl("\\"+ uploadsPath.resolve(cvPath).resolve(file.getOriginalFilename()));
            updateCV.setFileName(file.getOriginalFilename());
            cvService.save(updateCV);
            return "false";
        }
    }

    @PostMapping("/deleteCv")
    public String deleteCv(@RequestParam int idCv, HttpServletRequest request) {
        cvService.deleteById(idCv);
        String referer = request.getHeader("Referer");
        return "redirect:"+referer;
    }

    @PostMapping("/upload-company")
    @ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("email") String email) {
        // tạo đường dẫn cho thư mục lưu ảnh
        Path staticPath = Paths.get("static");
        Path uploadsPath = Paths.get("uploads");
        Path imagePath = Paths.get("image");
        boolean b = Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(uploadsPath).resolve(imagePath));
        if (!b){
            try {
                Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(uploadsPath).resolve(imagePath));
            } catch (IOException e) {
                return "Error";
            }
        }
        // Lưu ảnh vào thư mục
        Path imageFile = CURRENT_FOLDER.resolve(staticPath).resolve(uploadsPath)
                .resolve(imagePath).resolve(file.getOriginalFilename());
        try (OutputStream os = Files.newOutputStream(imageFile)) {
            os.write(file.getBytes());
        } catch (Exception e) {
            return "Error";
        }
        // Lưu đường dẫn ảnh mới vào database
        User user = userService.findUserByEmail(email);
        Company company = companyService.findByUserId(user.getId());
        company.setLogo("\\"+ uploadsPath.resolve(imagePath).resolve(file.getOriginalFilename()));
        companyService.save(company);
        return company.getLogo();
    }

    // Xử lý nộp CV đối với CV mới tải lên, chưa có trên hệ thống
    @PostMapping("/apply-job")
    @ResponseBody
    public String applyJob(@RequestParam("file") MultipartFile file, @RequestParam int idRe, @RequestParam("text") String text, HttpSession session) {
        // tìm cv của tài khoản user đang sử dụng
        User user = (User) session.getAttribute("user");

        // tạo đường dẫn cho thư mục lưu file
        Path staticPath = Paths.get("static");
        Path uploadsPath = Paths.get("uploads");
        Path cvPath = Paths.get("cv");
        boolean b = Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(uploadsPath).resolve(cvPath));
        if (!b){
            try {
                Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(uploadsPath).resolve(cvPath));
            } catch (IOException e) {
                return e.getMessage();
            }
        }
        // Lưu file vào thư mục
        Path cvFile = CURRENT_FOLDER.resolve(staticPath).resolve(uploadsPath)
                .resolve(cvPath).resolve(file.getOriginalFilename());
        try (OutputStream os = Files.newOutputStream(cvFile)) {
            os.write(file.getBytes());
        } catch (Exception e) {
            return e.getMessage();
        }
        // lưu CV
        CV newCV = new CV();
        newCV.setFileName(file.getOriginalFilename());
        newCV.setCvUrl("\\"+ uploadsPath.resolve(cvPath).resolve(file.getOriginalFilename()));
        cvService.save(newCV);
        // tạo và truyền thông tin cho lần apply CV mới
        if (applyPostService.findByRecruitmentIdAndUserId(idRe, user.getId())==null) {
            // tạo và truyền thông tin cho lần apply CV mới
            ApplyPost newApplyPost = new ApplyPost();
            newApplyPost.setRecruitment(recruitmentService.findById(idRe));
            newApplyPost.setUser(user);
            newApplyPost.setNameCv(newCV.getFileName());
            newApplyPost.setStatus(0);
            newApplyPost.setText(text);
            applyPostService.save(newApplyPost);
            return "true";
        } else {
            return "false";
        }
    }

    // Xử lý nộp CV đối với CV có sẵn trên hệ thống
    @PostMapping("/apply-job1")
    @ResponseBody
    public String applyJob1(HttpSession session, @RequestParam int idRe, @RequestParam String text) {
        // tìm cv của tài khoản user đang sử dụng
        User user = (User) session.getAttribute("user");
        CV cv = cvService.findByUserId(user.getId());

        if (applyPostService.findByRecruitmentIdAndUserId(idRe, user.getId())==null) {
            // tạo và truyền thông tin cho lần apply CV mới
            ApplyPost newApplyPost = new ApplyPost();
            newApplyPost.setRecruitment(recruitmentService.findById(idRe));
            newApplyPost.setUser(user);
            newApplyPost.setNameCv(cv.getFileName());
            newApplyPost.setStatus(0);
            newApplyPost.setText(text);
            applyPostService.save(newApplyPost);
            return "true";
        } else {
            return "false";
        }
    }

    @GetMapping("/approve/{userId}/{recruitmentId}")
    public String approveCV(@PathVariable int userId, @PathVariable int recruitmentId, HttpServletRequest request, RedirectAttributes attributes) {
        // phê duyệt post
        ApplyPost applyPost = applyPostService.findByRecruitmentIdAndUserId(recruitmentId, userId);
        applyPost.setStatus(1);
        applyPostService.save(applyPost);

        attributes.addFlashAttribute("success", "success");
        String referer = request.getHeader("Referer");
        return "redirect:"+referer;
    }
}
