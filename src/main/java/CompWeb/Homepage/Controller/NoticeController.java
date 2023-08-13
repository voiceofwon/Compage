package CompWeb.Homepage.Controller;


import CompWeb.Homepage.DTO.*;
import CompWeb.Homepage.Entity.File;
import CompWeb.Homepage.Entity.NoticeFile;
import CompWeb.Homepage.Repository.FileRepository;
import CompWeb.Homepage.Repository.NoticeFileRepository;
import CompWeb.Homepage.Service.FileService;
import CompWeb.Homepage.Service.NoticePostService;
import CompWeb.Homepage.Service.SosPostService;
import CompWeb.Homepage.Service.TopFixedPostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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


//공지사항 Controller
@Controller
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private NoticePostService noticePostService;

    @Autowired
    private NoticeFileRepository noticeFileRepository;

    @Autowired
    private TopFixedPostService topFixedPostService;

    @GetMapping
    //공지사항 List
    public String Notice(Model model){
        //공지사항 DTO LIST
        List<NoticePostDTO> noticePostDTOList = noticePostService.getPostList();
        //상단 고정 공지 DTO LIST
        List<TopFixedPostDTO> topFixedPostDTOList = topFixedPostService.getPostList();
        model.addAttribute("postList",noticePostDTOList);
        model.addAttribute("TopPostList", topFixedPostDTOList);

        return "Notice/notice.html";
    }

    @GetMapping("/noticePost")
    //공지사항 글쓰기 page
    public  String post(){
        return "Notice/noticePost.html";
    }

    @PostMapping("/noticePost")
    //공지사항 글쓰기 요청 POST
    public String write(@Valid NoticePostDTO noticePostDTO) throws IOException {
        noticePostService.savePost(noticePostDTO);
        return "redirect:/notice";
    }

    @GetMapping("/noticePost/{id}")
    //공지사항 조회
    public  String detail(@PathVariable("id")Long id, Model model){
        //게시글 세부정보, 첨부파일 load
        GetNoticePostDTO getNoticePostDTO = noticePostService.getPost(id);
        NoticeFile file = getNoticePostDTO.getFile();
        model.addAttribute("post",getNoticePostDTO);
        model.addAttribute("file",file);
        return "Notice/noticeDetail.html";
    }

    @GetMapping("/noticePost/attach/{id}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long id) throws MalformedURLException {
        NoticeFile file = noticeFileRepository.findById(id).orElse(null);

        UrlResource resource = new UrlResource("file:"+file.getSavedPath());

        String encodedFileName = UriUtils.encode(file.getOrgNm(), StandardCharsets.UTF_8);

        String contentDisposition = "attachment; filename=\""+encodedFileName+"\"";

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,contentDisposition).body(resource);

    }

    @GetMapping("/noticePost/delete/{id}")
    //공지사항 삭제
    public String deletePost(@PathVariable Long id){
        noticePostService.deletePost(id);
        return "redirect:/notice";

    }

    @GetMapping("/noticePost/edit/{id}")
    //공지사항 수정
    public String editPage(@PathVariable Long id, Model model){
        //기존 게시글 load를 위한 Model
        GetNoticePostDTO post = noticePostService.getPost(id);
        model.addAttribute("post",post);
        return "Notice/noticeEdit.html";
    }

    @PostMapping("/noticePost/edit/{id}")
    //공지사항 수정 요청 POST
    public String editPost(@PathVariable Long id, @Valid NoticePostDTO noticePostDTO) throws IOException{
        noticePostService.editPost(id,noticePostDTO);
        return "redirect:/notice";
    }
}
