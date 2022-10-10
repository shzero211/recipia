package com.ll.exam.RecipiaProject.mypage;

import com.ll.exam.RecipiaProject.user.SiteUser;
import com.ll.exam.RecipiaProject.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RequestMapping("/mypage") // 여기에 적으면 MyPageController URL 상위
@Controller
@RequiredArgsConstructor // 생성자 주입.
public class MyPageController {

    private final UserRepository userRepository;

    private final MyPageService myPageService;

    @GetMapping("")
    @PreAuthorize("isAuthenticated()")
    public String mypageHome(Model model, MyPageDto myPageDto, Principal principal) {

        if (principal != null) {
            System.out.println("타입정보 : " + principal.getClass());
            System.out.println("ID정보 : " + principal.getName());
        }

        SiteUser siteUser = myPageService.getUser(principal.getName());
        MyPageDto _myPageDto = MyPageDto.createMyPageDto(siteUser);
        model.addAttribute("myPageDto", _myPageDto);

        return "mypage/mypage_home";
    }


    @GetMapping("/allergy")
    @PreAuthorize("isAuthenticated()")
    public String myPageAllergyFilter(Principal principal) {
        return "mypage/mypage_allergyFilter";
    }

    @GetMapping("/modify")
    @PreAuthorize("isAuthenticated()")
    public String showModify(MyPageDto myPageDto, Principal principal, Model model) {

        SiteUser siteUser = myPageService.getUser(principal.getName());
        MyPageDto _myPageDto = MyPageDto.createMyPageDto(siteUser);
        model.addAttribute("myPageDto", _myPageDto);

        return "mypage/mypage_userModify";
    }

    @PostMapping("/modify")
    @PreAuthorize("isAuthenticated()")
    public String modify(@Valid MyPageDto myPageDto, Principal principal, Model model, String password) {
        SiteUser siteUser = myPageService.getUser(principal.getName());

        myPageService.modify(siteUser, myPageDto.getEmail(), myPageDto.getNickname(), myPageDto.getGender());
        return "redirect:/mypage";
    }


    @GetMapping("/withdraw")
    @PreAuthorize("isAuthenticated()")
    public String userWithdraw(Principal principal) {
        SiteUser siteUser = myPageService.getUser(principal.getName());


        myPageService.delete(siteUser);
        return "redirect:/user/logout";
    }

    @GetMapping("/check-pwd")
    @PreAuthorize("isAuthenticated()")
    public String checkPwdView(){

        return "mypage/check-pwd";
    }

    /** 회원 수정 전 비밀번호 확인 **/
    @GetMapping("/api/check-pwd")
    @ResponseBody
    public boolean checkPassword(Principal principal,
                                 @RequestParam String checkPassword,
                                 Model model){
        String userName= principal.getName();
        return myPageService.checkPassword(userName, checkPassword);
    }

}