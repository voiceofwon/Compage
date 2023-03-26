package CompWeb.Homepage.Controller;


import CompWeb.Homepage.DTO.*;
import CompWeb.Homepage.Entity.Member;
import CompWeb.Homepage.Repository.MemberRepository;
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
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;

    private final HistroyService histroyService;

    private final MemberRepository memberRepository;

    private final TopFixedPostService topFixedPostService;

    @GetMapping
    public String admin(){return "Admin/admin.html";}

    @GetMapping("/joinAdmin")
    public String joinAdminPage(){return "Member/adminJoin.html";}


    //기존 관리자가 회원, 신규 관리자의 회원가입을 해줘야 함.
    @PostMapping("/joinAdmin")
    public String joinAdmin(AdminJoinDTO adminJoinDTO){
        memberService.joinAdmin(adminJoinDTO);
        return "redirect:/admin";
    }

    @GetMapping("/joinMember")
    public String joinMemberPage() {return "Member/memberJoin.html";}

    @PostMapping("/joinMember")
    public String joinMember(@Valid MemberJoinDTO memberJoinDTO) throws IOException {
        memberService.joinMember(memberJoinDTO);
        return "redirect:/admin";
    }

    @GetMapping("/memberList")
    public String getMemberList(Model model){
        model.addAttribute("memberList",memberService.getMemberList());

        return "Admin/memberList.html";
    }

    @GetMapping("/memberList/{id}")
    public String modifyMemberPage(@PathVariable Long id, Model model){
        model.addAttribute("id",id);
        return "Admin/memberModify.html";
    }
    @PostMapping("/memberList/{id}")
    public String modifyMember(@PathVariable Long id, @Valid MemberModifyDTO memberModifyDTO){
        Member member = memberService.findById(id);
        member.setGrade(memberModifyDTO.getGrade());
        memberRepository.save(member);
        return "redirect:/admin/memberList";

    }

    @GetMapping("/memberList/delete")
    public String deleteMemberPage(){
        return "Admin/memberDelete.html";

    }

    @PostMapping("/memberList/delete")
    public String deleteMember(@Valid MemberDeleteDTO memberDeleteDTO){
        memberService.deleteMember(memberDeleteDTO.getUsername());
        return "redirect:/admin/memberList";
    }


    @GetMapping("/history")
    public String history(Model model){
        model.addAttribute("historyList",histroyService.getHistoryList());
        return "Admin/history.html";
    }

    @GetMapping("/history/add")
    public String addHistoryPage(){
        return "Admin/addHistory.html";
    }

    @PostMapping("/history/add")
    public String addHistory(@Valid AddHistoryDTO addHistoryDTO){
        histroyService.addHistory(addHistoryDTO);

        return "redirect:/admin/history";

    }

    @GetMapping("/history/delete")
    public String deleteHistory(){
        histroyService.deleteHistory();

        return "redirect:/admin/history";
    }

    @GetMapping("/postTopFixedPost")
    public String topFixedPostPage(){

        return "TopFixedPost/topFixedPost.html";
    }

    @PostMapping("/postTopFixedPost")
    public String topFixedPost(@Valid TopFixedPostDTO topFixedPostDTO) throws IOException{
        topFixedPostService.savePost(topFixedPostDTO);

        return "redirect:/admin";
    }



}
