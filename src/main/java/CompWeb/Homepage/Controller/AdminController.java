package CompWeb.Homepage.Controller;


import CompWeb.Homepage.DTO.AdminJoinDTO;
import CompWeb.Homepage.DTO.MemberDeleteDTO;
import CompWeb.Homepage.DTO.MemberJoinDTO;
import CompWeb.Homepage.Service.MemberService;
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
    public String joinMember(MemberJoinDTO memberJoinDTO) throws IOException {
        memberService.joinMember(memberJoinDTO);
        return "redirect:/admin";
    }

    @GetMapping("/memberList")
    public String getMemberList(Model model){
        model.addAttribute("memberList",memberService.getMemberList());

        return "Admin/memberList.html";
    }

    @GetMapping("/memberList/delete")
    public String deleteMemberPage(){
        return "Admin/memberDelete.html";

    }

    @PostMapping("/memberList/delete")
    public String deleteMember(MemberDeleteDTO memberDeleteDTO){
        memberService.deleteMember(memberDeleteDTO.getUsername());
        return "redirect:/admin/memberList";
    }




}
