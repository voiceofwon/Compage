package CompWeb.Homepage.Controller;


import CompWeb.Homepage.DTO.GetPostDTO;
import CompWeb.Homepage.DTO.SosPostDTO;
import CompWeb.Homepage.DTO.TopFixedPostDTO;
import CompWeb.Homepage.Entity.File;
import CompWeb.Homepage.Repository.FileRepository;
import CompWeb.Homepage.Service.FileService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;


//학습 자료 게시판 Controller
@Controller
@RequestMapping("/Sos")
public class SosController {

    @Autowired
    private SosPostService sosPostService;

    @Autowired
    private FileService fileService;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private TopFixedPostService topFixedPostService;

    @GetMapping
    //학습 자료 게시판 LIST
    public String Sos(Model model){
        //게시글 LIST
        List<SosPostDTO> sosPostDTOList = sosPostService.getPostList();
        //상단 고정 공지 LIST
        List<TopFixedPostDTO> topFixedPostDTOList = topFixedPostService.getPostList();
        model.addAttribute("postList",sosPostDTOList);
        model.addAttribute("TopPostList", topFixedPostDTOList);

        return "Sos/list.html";
    }

    @GetMapping("/post")
    //학습자료 게시판 글쓰기 page
    public  String post(){
        return "Sos/post.html";
    }

    @PostMapping("/post")
    //학습자료 게시판 글쓰기 요청 POST
    public String write(@Valid SosPostDTO sosPostDTO) throws IOException{
        sosPostService.savePost(sosPostDTO);
        return "redirect:/Sos";

    }

    @GetMapping("/post/{id}")
    //게시글 조회
    public  String detail(@PathVariable("id")Long id, Model model){
        //게시글 세부정보 및 첨부파일 load
        GetPostDTO getPostDTO = sosPostService.getPost(id);
        File file = getPostDTO.getFile();
        model.addAttribute("post",getPostDTO);
        model.addAttribute("file",file);
        return "Sos/detail.html";
    }

    @GetMapping("/post/attach/{id}")
    //게시글 첨부파일 다운로드
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long id) throws MalformedURLException{
        File file = fileRepository.findById(id).orElse(null);

        UrlResource resource = new UrlResource("file:"+file.getSavedPath());

        String encodedFileName = UriUtils.encode(file.getOrgNm(),StandardCharsets.UTF_8);

        String contentDisposition = "attachment; filename=\""+encodedFileName+"\"";

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,contentDisposition).body(resource);

    }

    @GetMapping("/post/delete/{id}")
    //게시글 삭제
    public String deletePost(@PathVariable Long id){
        sosPostService.deletePost(id);
        return "redirect:/Sos";

    }

    @GetMapping("/post/edit/{id}")
    //게시글 수정
    public String editPage(@PathVariable Long id, Model model){
        //기존 글을 불러오기 위한 model
        GetPostDTO post = sosPostService.getPost(id);
        model.addAttribute("post",post);
        return "Sos/edit.html";
    }

    @PostMapping("/post/edit/{id}")
    //게시글 수정 요청 POST
    public String editPost(@PathVariable Long id,@Valid SosPostDTO sosPostDTO) throws IOException{
        sosPostService.editPost(id,sosPostDTO);
        return "redirect:/Sos";
    }
}
