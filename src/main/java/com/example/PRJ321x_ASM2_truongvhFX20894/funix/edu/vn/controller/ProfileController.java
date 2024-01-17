package com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.controller;

import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.model.Company;
import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.model.User;
import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.service.CompanyService;
import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final UserService userService;
    private final CompanyService companyService;
    private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));

    @Autowired
    public ProfileController(UserService userService, CompanyService companyService) {
        this.userService = userService;
        this.companyService = companyService;
    }

    @PostMapping("/confirm-account")
    public String confirmAccount(@RequestParam("email") String email, HttpServletRequest request, RedirectAttributes attributes) {
        User user = userService.findUserByEmail(email);
        if (user!=null) {
            user.setStatus(1);
            userService.save(user);
            attributes.addFlashAttribute("confirm_await", "confirm");
            String referer = request.getHeader("Referer");
            return "redirect:"+referer;
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("update-profile")
    public String updateProfile(@ModelAttribute("updateUser") User user, HttpServletRequest request) {
        User tempUser = userService.findById(user.getId());
        tempUser.setEmail(user.getEmail());
        tempUser.setFullName(user.getFullName());
        tempUser.setAddress(user.getAddress());
        tempUser.setPhoneNumber(user.getPhoneNumber());
        tempUser.setDescription(user.getDescription());
        userService.save(tempUser);
        String referer = request.getHeader("Referer");
        return "redirect:"+referer;
    }

    @PostMapping("update-company")
    public String updateCompany(@ModelAttribute("updateCompany") Company company, HttpServletRequest request) {
        Company tempCompany = companyService.findById(company.getId());
        tempCompany.setEmail(company.getEmail());
        tempCompany.setNameCompany(company.getNameCompany());
        tempCompany.setAddress(company.getAddress());
        tempCompany.setPhoneNumber(company.getPhoneNumber());
        tempCompany.setDescription(company.getDescription());
        companyService.save(tempCompany);
        String referer = request.getHeader("Referer");
        return "redirect:"+referer;
    }

    @PostMapping("/upload")
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
        user.setImage("\\"+ uploadsPath.resolve(imagePath).resolve(file.getOriginalFilename()));
        userService.save(user);
        return user.getImage();
    }
}
