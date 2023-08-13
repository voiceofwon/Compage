package CompWeb.Homepage.Controller;


import CompWeb.Homepage.DTO.*;
import CompWeb.Homepage.Entity.ActivityFile;
import CompWeb.Homepage.Entity.NoticeFile;
import CompWeb.Homepage.Repository.ActivityFileRepository;
import CompWeb.Homepage.Service.ActivityPostService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/activity")
public class ActivityController {

    private final ActivityPostService activityPostService;
    private final ActivityFileRepository activityFileRepository;
    private final TopFixedPostService topFixedPostService;

    @GetMapping
    public String Activity(Model model){
        //활동내역 게시글 DTO LIST
        List<ActivityPostDTO> activityPostDTOList = activityPostService.getPostList();
        //상단고정 공지 DTO LIST
        List<TopFixedPostDTO> topFixedPostDTOList = topFixedPostService.getPostList();

        model.addAttribute("postList",activityPostDTOList);
        model.addAttribute("TopPostList", topFixedPostDTOList);

        return "Activity/activityList.html";
    }

    @GetMapping("/activityPost")
    //글쓰기 페이지 load
    public String post(){return "Activity/activityPost.html";}

    @PostMapping("/activityPost")
    //글쓰기 post 요청
    public String write(@Valid ActivityPostDTO activityPostDTO) throws IOException{
        activityPostService.savePost(activityPostDTO);
        return "redirect:/activity";
    }

    @GetMapping("/activityPost/{id}")
    //게시글 조회
    public String detail(@PathVariable("id")Long id,Model model){
        //게시글 세부정보 및 file load
        GetActivityPostDTO getActivityPostDTO = activityPostService.getPost(id);
        ActivityFile file = getActivityPostDTO.getFile();
        model.addAttribute("post",getActivityPostDTO);
        model.addAttribute("file",file);
        return "Activity/activityDetail.html";
    }

    @GetMapping("/activityPost/attach/{id}")
    //첨부파일 다운로드
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long id) throws MalformedURLException {
        //DB 조회
        ActivityFile file = activityFileRepository.findById(id).orElse(null);

        //WebConfig에 설정한 file directory 접근 URL을 이용한 url 다운로드 url 재설정
        UrlResource resource = new UrlResource("file:"+file.getSavedPath());

        //저장된 fileName encode(UTF-8)
        String encodedFileName = UriUtils.encode(file.getOrgNm(), StandardCharsets.UTF_8);

        String contentDisposition = "attachment; filename=\""+encodedFileName+"\"";

        //다운로드 요청 Response 반환
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,contentDisposition).body(resource);

    }

    @GetMapping("/activityPost/delete/{id}")
    //게시글 삭제
   public String deletePost(@PathVariable Long id){
        activityPostService.deletePost(id);
        return "redirect:/activity";
    }

    @GetMapping("/activityPost/edit/{id}")
    //게시글 수정
    public String editPage(@PathVariable Long id, Model model){
        //기존 게시글 정보 load를 위한 DTO
        GetActivityPostDTO post = activityPostService.getPost(id);
        model.addAttribute("post",post);
        return "Activity/activityEdit.html";
    }

    @PostMapping("/activityPost/edit/{id}")
    //게시글 수정 요청 POST
    public String editPost(@PathVariable Long id, @Valid ActivityPostDTO activityPostDTO) throws IOException{
        activityPostService.editPost(id,activityPostDTO);
        return "redirect:/activity";
    }
}
