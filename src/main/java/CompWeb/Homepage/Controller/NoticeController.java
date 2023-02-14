package CompWeb.Homepage.Controller;


import CompWeb.Homepage.DTO.GetNoticePostDTO;
import CompWeb.Homepage.DTO.GetPostDTO;
import CompWeb.Homepage.DTO.NoticePostDTO;
import CompWeb.Homepage.DTO.SosPostDTO;
import CompWeb.Homepage.Entity.File;
import CompWeb.Homepage.Entity.NoticeFile;
import CompWeb.Homepage.Repository.FileRepository;
import CompWeb.Homepage.Repository.NoticeFileRepository;
import CompWeb.Homepage.Service.FileService;
import CompWeb.Homepage.Service.NoticePostService;
import CompWeb.Homepage.Service.SosPostService;
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

@Controller
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private NoticePostService noticePostService;

    @Autowired
    private NoticeFileRepository noticeFileRepository;

    @GetMapping
    public String Sos(Model model){
        List<NoticePostDTO> noticePostDTOList = noticePostService.getPostList();
        model.addAttribute("postList",noticePostDTOList);

        return "Notice/notice.html";
    }

    @GetMapping("/noticePost")
    public  String post(){
        return "Notice/noticePost.html";
    }

    @PostMapping("/noticePost")
    public String write(NoticePostDTO noticePostDTO) throws IOException {
        noticePostService.savePost(noticePostDTO);
        return "redirect:/notice";

    }

    @GetMapping("/noticePost/{id}")
    public  String detail(@PathVariable("id")Long id, Model model){
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
    public String deletePost(@PathVariable Long id){
        noticePostService.deletePost(id);
        return "redirect:/notice";

    }

    @GetMapping("/noticePost/edit")
    public String editPage(){
        return "Notice/noticePost.html";
    }

    @PostMapping("/noticePost/edit/{id}")
    public String editPost(@PathVariable Long id, NoticePostDTO noticePostDTO) throws IOException{
        noticePostService.editPost(id,noticePostDTO);
        return "redirect:/notice";
    }
}
