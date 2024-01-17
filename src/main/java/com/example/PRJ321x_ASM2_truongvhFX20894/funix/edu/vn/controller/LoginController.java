package com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.controller;

import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.model.Role;
import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.model.User;
import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.service.RoleService;
import com.example.PRJ321x_ASM2_truongvhFX20894.funix.edu.vn.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

@Controller
@RequestMapping("/auth")
public class LoginController {
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public LoginController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "public/login";
    }

    @PostMapping("/register")
    public String register(@RequestParam("rePassword") String rePassword,
                           @ModelAttribute("newUser") User newUser,
                           @RequestParam("role_id") int roleId,
                           HttpServletRequest request, RedirectAttributes result) {

        // Kiểm tra mật khẩu đã được nhập lại đúng chưa
        if ((!Objects.equals(rePassword, newUser.getPassword())) || (roleId==-1)) {
            result.addFlashAttribute("msg_register_error", "msg");
            String referer = request.getHeader("Referer");
            return "redirect:"+referer;
        }

        Role role = roleService.findById(roleId);
        newUser.setRole(role);
        userService.save(newUser);
        //Tạo thông báo sau khi đã đăng ký thành công
        result.addFlashAttribute("msg_register_success", "msg");
        String referer = request.getHeader("Referer");
        return "redirect:"+referer;
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password,
                        HttpSession session, RedirectAttributes result, Model model, HttpServletRequest request) {
        model.addAttribute("email", email);
        model.addAttribute("password", password);

        User user = userService.findUserByEmailAndPassword(email, password);
        // truyền thông tin User vào phiên đăng nhập
        if (user!=null) {
            session.setAttribute("user", user);
            if (Objects.equals(user.getRole().getRoleName(), "USER")) {
                session.setAttribute("userAcc", "user");
            } else {
                session.setAttribute("recruiterAcc", "recruiter");
            }
            result.addFlashAttribute("msg_login_success", "msg");
            return "redirect:/";
        } else {
            result.addFlashAttribute("errorLogin", "Error");
            String referer = request.getHeader("Referer");
            return "redirect:"+referer;
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletRequest request) {
        session = request.getSession();
        session.invalidate();
        // trả về trang chủ lúc chưa đăng nhập
        return "redirect:/";
    }
}
