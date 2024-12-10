package com.applestore.applestore.Controllers;

import com.applestore.applestore.DTOs.RegisterDto;
import com.applestore.applestore.Entities.User;
import com.applestore.applestore.Exception.UserNotFoundException;
import com.applestore.applestore.Services.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import net.bytebuddy.utility.RandomString;
import org.springframework.boot.Banner;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.MailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.UnsupportedEncodingException;

@Controller
public class AuthController {
    private final UserService userService;
    private final JavaMailSender mailSender;

    public AuthController(UserService userService, JavaMailSender mailSender) {
        this.userService = userService;
        this.mailSender = mailSender;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "/Fragments/auth/login";
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        RegisterDto user = new RegisterDto();
        model.addAttribute("user", user);
        return "/Fragments/auth/register";
    }
    @GetMapping("/register/admin")
    public String getRegisterForAdminForm(Model model) {
        RegisterDto user = new RegisterDto();
        model.addAttribute("user", user);
        return "/Fragments/auth/register-admin";
    }

    //
    @PostMapping("/register")
    public String register(@ModelAttribute("user") RegisterDto user
            ,BindingResult result, Model model
    ) {
        System.out.println("User"+user);
        User userEntity = userService.findByGmail(user.getGmail());
        if(userEntity != null){
            return "redirect:/register?fail";
        }
        userService.saveUser(user);
        return "redirect:/register?success";
    }
    @PostMapping("/register/admin")
    public String registerForAdmin(@ModelAttribute("user") RegisterDto user
            ,BindingResult result, Model model
    ){
        System.out.println("User"+user);
        userService.saveAdmin(user);
        return "redirect:/register/admin?success";
    }
    @GetMapping("/forgot-password")
    public String forgetPasswordForm(Model model) {
        model.addAttribute("pageTitle", "Forgot Password");
        return "/Fragments/auth/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgetPassword(HttpServletRequest req, Model model) {
        String gmail = req.getParameter("gmail");
        String token = RandomString.make(50);


        System.out.println("Gmail: "+gmail);
        System.out.println("Token: "+token);
        try{
            userService.updateResetPasswordToken(token, gmail);
            String rspwLink = getSiteURL(req) + "/reset-password?token="+token;
            System.out.println(rspwLink);
            sendMail(gmail, rspwLink);
            model.addAttribute("message", "We have sent link to reset password, please check your mail");
        }catch (UserNotFoundException   ex){
            System.out.println("Gmail: "+gmail+" Not found");
            model.addAttribute("error", ex.getMessage());
        }catch (UnsupportedEncodingException | MessagingException ex){
            model.addAttribute("error", "Error while sending mail");
        }

        return "/Fragments/auth/forgot-password";
    }

    private void sendMail(String gmail, String rspwLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("addmmin4567@gmail.com", "AppleWorld");
        helper.setTo(gmail);
        String subject = "Đặt lại mật khẩu";
        String content = "<p>AppleWorld xin chào</p>"
                +"<p>Chúng tôi nhận được yêu cầu đặt lại mật khẩu từ bạn.</p>"
                +"<p>Vui lòng bấm vào liên kết bên dưới để tiến hành đặt lại mật khẩu.</p>"
                +"<p><b><a href=\""+rspwLink + "\">Đặt lại mật khẩu<a/></b></p>"
                +"<p>Vui lòng không phản hồi lại mail này hoặc bỏ qua mail này nếu bạn không còn nhu cầu nữa.</p>"
                +"<p>Trân trọng cảm ơn!.</p>";

        helper.setSubject(subject);
        helper.setText(content,true);
        mailSender.send(message);
    }

    public static String getSiteURL(HttpServletRequest req){
        String siteURL = req.getRequestURL().toString();
        System.out.println("SiteURL: "+siteURL);
        return siteURL.replace(req.getServletPath(),""); //remove /forgot-password in localhost/forgotpassword
    }

    @GetMapping("/reset-password")
    public String resetPasswordForm(@Param(value = "token") String token, Model model){
        User userEntity = userService.getByResetPasswordToken(token);
        if(userEntity ==  null){
            model.addAttribute("title", "Reset your password");
            model.addAttribute("message", "Invalid Token");
            return "/Fragments/auth/message";
        }
        model.addAttribute("token", token);
        model.addAttribute("pageTitle", "Reset Your Password");
        return "/Fragments/auth/reset-password-form";
    }
    @PostMapping("/reset-password")
    public String resetPasswordForm(Model model, HttpServletRequest req){
        String token = req.getParameter("token");
        String newPassword = req.getParameter("password");
        User userEntity = userService.getByResetPasswordToken(token);
        if(userEntity ==  null){
            model.addAttribute("title", "Reset your password");
            model.addAttribute("message", "Invalid Token");

        }else{
            userService.updatePassword(userEntity, newPassword);
            model.addAttribute("message", "You have successfully changed your password");
        }
        return "redirect:/login";
    }
}
