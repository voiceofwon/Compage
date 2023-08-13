package CompWeb.Homepage.Controller;


import CompWeb.Homepage.DTO.*;
import CompWeb.Homepage.Entity.Member;
import CompWeb.Homepage.Repository.MemberRepository;
import CompWeb.Homepage.Service.HistroyService;
import CompWeb.Homepage.Service.MemberService;
import CompWeb.Homepage.Service.TopFixedPostService;
import CompWeb.Homepage.Service.VisitorScheduler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;

//관리자 페이지
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;

    private final HistroyService histroyService;

    private final MemberRepository memberRepository;

    private final TopFixedPostService topFixedPostService;

    private final VisitorScheduler visitorScheduler;

    @GetMapping
    //관리자 페이지
    public String admin(Model model){
        //방문자 수 모니터(금일 방문자, 누적 방문자, 월별 방문자)
        model.addAttribute("Today", visitorScheduler.DayVisitor(LocalDate.now()));
        model.addAttribute("Total", visitorScheduler.TotalVisitor());
        model.addAttribute("Month", Arrays.toString(visitorScheduler.ThisYearMonth()));
        return "Admin/admin_new.html";
    }

    //회원가입
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


    //회원관리
    @GetMapping("/memberList")
    public String getMemberList(Model model){
        model.addAttribute("memberList",memberService.getMemberList());

        return "Admin/memberList.html";
    }

    @GetMapping("/memberList/{id}")
    //기존 멤버 학년 수정
    public String modifyMemberPage(@PathVariable Long id, Model model){
        model.addAttribute("id",id);
        return "Admin/memberModify.html";
    }
    @PostMapping("/memberList/{id}")
    //기존 멤버 학년 수정 요청 POST
    public String modifyMember(@PathVariable Long id, @Valid MemberModifyDTO memberModifyDTO){
        Member member = memberService.findById(id);
        member.setGrade(memberModifyDTO.getGrade());
        memberRepository.save(member);
        return "redirect:/admin/memberList";

    }

    @GetMapping("/memberList/delete")
    //멤버 삭제(탈퇴)
    public String deleteMemberPage(){
        return "Admin/memberDelete.html";

    }

    @PostMapping("/memberList/delete")
    //멤버 삭제(탈퇴) 요청 POST
    public String deleteMember(@Valid MemberDeleteDTO memberDeleteDTO){
        memberService.deleteMember(memberDeleteDTO.getUsername());
        return "redirect:/admin/memberList";
    }


    //연혁관리
    @GetMapping("/history")
    public String history(Model model){
        model.addAttribute("historyList",histroyService.getHistoryList());
        return "Admin/history.html";
    }

    @GetMapping("/history/add")
    //연혁추가 페이지
    public String addHistoryPage(){
        return "Admin/addHistory.html";
    }

    @PostMapping("/history/add")
    //연혁추가 요청 POST
    public String addHistory(@Valid AddHistoryDTO addHistoryDTO){
        histroyService.addHistory(addHistoryDTO);

        return "redirect:/admin/history";

    }

    @GetMapping("/history/delete")
    //연혁 삭제(전체삭제)
    public String deleteHistory(){
        histroyService.deleteHistory();

        return "redirect:/admin/history";
    }


    //상단고정 공지 관리
    @GetMapping("/postTopFixedPost")
    //상단고정 공지 글쓰기 페이지
    public String topFixedPostPage(){
        return "TopFixedPost/topFixedPost.html";
    }

    @PostMapping("/postTopFixedPost")
    //상단고정 공지 글쓰기 요청 POST
    public String topFixedPost(@Valid TopFixedPostDTO topFixedPostDTO) throws IOException{
        topFixedPostService.savePost(topFixedPostDTO);

        return "redirect:/admin";
    }



}
