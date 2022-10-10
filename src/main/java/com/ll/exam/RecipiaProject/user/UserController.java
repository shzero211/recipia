package com.ll.exam.RecipiaProject.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private int tempNum;
    private String tempEmail;
    private final UserService userService;

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/sign-up")
    public String signUpForm(UserFormDto userFormDto){
        return "user/signup_form";
    }

    @PostMapping("/sign-up")
    public String signUpFormPost(@Valid UserFormDto userFormDto, BindingResult bindingResult){
        // 만일 오류가 있다면 다시 회원가입 입력 폼으로
        if (bindingResult.hasErrors()){
            return "user/signup_form";
        }
        if (!userFormDto.getPassword1().equals(userFormDto.getPassword2())){
            bindingResult.rejectValue("password2","passwordInCorrect"
                    ,"비밀번호가 일치하지 않습니다.");
            return "user/signup_form";
        }
        try{
            userService.create(userFormDto.getUsername(), userFormDto.getPassword1(), userFormDto.getEmail());
        }catch (UsernameDuplicatedException e){
            bindingResult.reject("usernameDuplicatedError", e.getMessage());
            return "user/signup_form";
        }catch (EmailDuplicatedException e){
            bindingResult.reject("emailDuplicatedError", e.getMessage());
            return "user/signup_form";
        }
        return "redirect:/user/home";
    }

    @GetMapping("/login")
    public String userLogin(){
        return "user/login_form";
    }

    @GetMapping("/find-password")
    public String findPassword(){
        return "user/find_pw_form";
    }

    @PostMapping("/find-password")
    public String findPasswordPost(Model model, @RequestParam("email") String email){
        if (!userService.checkEmail(email)){
            return "user/find_pw_form";
        }
        tempNum = userService.sendEmail(email);
        model.addAttribute("tempNum", tempNum);
        tempEmail = email;
        return "user/certification_num";
    }

    @GetMapping("/temp-num")
    public String tempNum(){
        return "user/certification_num";
    }

    @PostMapping("/temp-num")
    public String tempNumPost(Model model, @RequestParam("tempNum") String tempNum){
        if (!(this.tempNum == Integer.parseInt(tempNum))){
            return "user/certification_num";
        }
        return "user/modify_pw";
    }

    @GetMapping("/modify-pw")
    public String modifyPW(PasswordDto passwordDto){
        return "user/modify_pw";
    }

    @PostMapping("/modify-pw")
    public String modifyPWPost(Model model, @Valid PasswordDto passwordDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "user/modify_pw";
        }
        if (!passwordDto.getPassword1().equals(passwordDto.getPassword2())){
            bindingResult.rejectValue("password2","passwordInCorrect"
                    ,"비밀번호가 일치하지 않습니다.");
            return "user/modify_pw";
        }

        userService.updatePw(tempEmail, passwordDto.getPassword1());
        return "redirect:/user/home";
    }
}
