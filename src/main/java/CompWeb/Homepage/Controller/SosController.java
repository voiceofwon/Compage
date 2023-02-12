package CompWeb.Homepage.Controller;


import CompWeb.Homepage.DTO.GetPostDTO;
import CompWeb.Homepage.DTO.SosPostDTO;
import CompWeb.Homepage.Entity.File;
import CompWeb.Homepage.Repository.FileRepository;
import CompWeb.Homepage.Service.FileService;
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
@RequestMapping("/Sos")
public class SosController {

    @Autowired
    private SosPostService sosPostService;

    @Autowired
    private FileService fileService;

    @Autowired
    private FileRepository fileRepository;

    @GetMapping
    public String Sos(Model model){
        List<SosPostDTO> sosPostDTOList = sosPostService.getPostList();
        model.addAttribute("postList",sosPostDTOList);

        return "Sos/list.html";
    }

    @GetMapping("/post")
    public  String post(){
        return "Sos/post.html";
    }

    @PostMapping("/post")
    public String write(SosPostDTO sosPostDTO) throws IOException{
        sosPostService.savePost(sosPostDTO);
        return "redirect:/Sos";

    }

    @GetMapping("/post/{id}")
    public  String detail(@PathVariable("id")Long id, Model model){
        GetPostDTO getPostDTO = sosPostService.getPost(id);
        File file = getPostDTO.getFile();
        model.addAttribute("post",getPostDTO);
        model.addAttribute("file",file);
        return "Sos/detail.html";
    }

    @GetMapping("/post/attach/{id}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long id) throws MalformedURLException{
        File file = fileRepository.findById(id).orElse(null);

        UrlResource resource = new UrlResource("file:"+file.getSavedPath());

        String encodedFileName = UriUtils.encode(file.getOrgNm(),StandardCharsets.UTF_8);

        String contentDisposition = "attachment; filename=\""+encodedFileName+"\"";

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,contentDisposition).body(resource);

    }

    @GetMapping("/post/delete/{id}")
    public String deletePost(@PathVariable Long id){
        sosPostService.deletePost(id);
        return "redirect:/Sos";

    }

    @GetMapping("/post/edit")
    public String editPage(){
        return "Sos/post.html";
    }

    @PostMapping("/post/edit/{id}")
    public String editPost(@PathVariable Long id, SosPostDTO sosPostDTO) throws IOException{
        sosPostService.editPost(id,sosPostDTO);
        return "redirect:/Sos";
    }
}
