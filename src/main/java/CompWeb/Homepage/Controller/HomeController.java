package CompWeb.Homepage.Controller;

import CompWeb.Homepage.DTO.AdminJoinDTO;
import CompWeb.Homepage.Service.HistroyService;
import CompWeb.Homepage.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final HistroyService histroyService;

    private final MemberService memberService;
    @GetMapping("/")
    public String home1(){
        return"test.html";
    }
    @GetMapping
    public String home2(){
        return "test.html";
    }

    @GetMapping("/home")
    public String home(){
        return "test.html";
    }

    @GetMapping("/intro")
    public String intro(Model model){
        model.addAttribute("historyList",histroyService.getHistoryList());
        return "introduction.html";
    }
    @GetMapping("/intro/joinAdmin")
    public String joinAdminPage(){return "Member/FirstJoin.html";}


    //기존 관리자가 회원, 신규 관리자의 회원가입을 해줘야 함.
    @PostMapping("/intro/joinAdmin")
    public String joinAdmin(AdminJoinDTO adminJoinDTO){
        memberService.joinAdmin(adminJoinDTO);
        return "redirect:/home";
    }



}
