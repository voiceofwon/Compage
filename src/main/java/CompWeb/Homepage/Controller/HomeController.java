package CompWeb.Homepage.Controller;

import CompWeb.Homepage.DTO.AdminJoinDTO;
import CompWeb.Homepage.DTO.TopFixedPostDTO;
import CompWeb.Homepage.Entity.TopFixedPost;
import CompWeb.Homepage.Service.HistroyService;
import CompWeb.Homepage.Service.MemberService;
import CompWeb.Homepage.Service.TopFixedPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final HistroyService histroyService;

    private final MemberService memberService;

    private final TopFixedPostService topFixedPostService;
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

    @GetMapping("/topFixedPost/{id}")
    public String fixedPostDetail(@PathVariable("id")Long id, Model model){
        TopFixedPostDTO topFixedPostDTO = topFixedPostService.getPost(id);
        model.addAttribute("post",topFixedPostDTO);
        return "TopFixedPost/topFixedDetail.html";

    }

    @GetMapping("/topFixedPost/edit/{id}")
    public String fixedPostEditPage(@PathVariable Long id, Model model){
        TopFixedPostDTO post = topFixedPostService.getPost(id);
        model.addAttribute("post",post);
        return "TopFixedPost/topFixedEdit.html";
    }
    @PostMapping("/topFixedPost/edit/{id}")
    public String fixedPostEdit(@PathVariable Long id, @Valid TopFixedPostDTO topFixedPostDTO) throws IOException {
        topFixedPostService.editPost(id,topFixedPostDTO);
        return "redirect:/notice";
    }

    @GetMapping("/topFixedPost/delete/{id}")
    public String fixedPostDelete(@PathVariable Long id){
        topFixedPostService.deletePost(id);
        return "redirect:/notice";
    }



}
