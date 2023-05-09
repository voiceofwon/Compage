package CompWeb.Homepage.Controller;

import CompWeb.Homepage.DTO.AdminJoinDTO;
import CompWeb.Homepage.DTO.GetTopFixedPostDTO;
import CompWeb.Homepage.DTO.TopFixedPostDTO;
import CompWeb.Homepage.Entity.File;
import CompWeb.Homepage.Entity.TopFixedFile;
import CompWeb.Homepage.Entity.TopFixedPost;
import CompWeb.Homepage.Repository.TopFixedFileRepository;
import CompWeb.Homepage.Service.HistroyService;
import CompWeb.Homepage.Service.MemberService;
import CompWeb.Homepage.Service.TopFixedPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final HistroyService histroyService;

    private final MemberService memberService;

    private final TopFixedPostService topFixedPostService;

    private final TopFixedFileRepository topFixedFileRepository;
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
        GetTopFixedPostDTO getTopFixedPostDTO = topFixedPostService.getPost(id);
        TopFixedFile topFixedFile = getTopFixedPostDTO.getTopFixedFile();
        model.addAttribute("post",getTopFixedPostDTO);
        model.addAttribute("file", topFixedFile);
        return "TopFixedPost/topFixedDetail.html";

    }

    @GetMapping("/topFixedPost/attach/{id}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long id) throws MalformedURLException {
        TopFixedFile file = topFixedFileRepository.findById(id).orElse(null);

        UrlResource resource = new UrlResource("file:"+file.getSavedPath());

        String encodedFileName = UriUtils.encode(file.getOrgNm(), StandardCharsets.UTF_8);

        String contentDisposition = "attachment; filename=\""+encodedFileName+"\"";

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,contentDisposition).body(resource);

    }

    @GetMapping("/topFixedPost/edit/{id}")
    public String fixedPostEditPage(@PathVariable Long id, Model model){
        GetTopFixedPostDTO post = topFixedPostService.getPost(id);
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
