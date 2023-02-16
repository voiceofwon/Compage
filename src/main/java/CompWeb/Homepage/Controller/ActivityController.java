package CompWeb.Homepage.Controller;


import CompWeb.Homepage.DTO.ActivityPostDTO;
import CompWeb.Homepage.DTO.GetActivityPostDTO;
import CompWeb.Homepage.DTO.GetPostDTO;
import CompWeb.Homepage.DTO.NoticePostDTO;
import CompWeb.Homepage.Entity.ActivityFile;
import CompWeb.Homepage.Entity.NoticeFile;
import CompWeb.Homepage.Repository.ActivityFileRepository;
import CompWeb.Homepage.Service.ActivityPostService;
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


    @GetMapping
    public String Activity(Model model){
        List<ActivityPostDTO> activityPostDTOList = activityPostService.getPostList();
        model.addAttribute("postList",activityPostDTOList);

        return "Activity/activityList.html";
    }

    @GetMapping("/activityPost")
    public String post(){return "Activity/activityPost.html";}

    @PostMapping("/activityPost")
    public String write(ActivityPostDTO activityPostDTO) throws IOException{
        activityPostService.savePost(activityPostDTO);
        return "redirect:/activity";
    }

    @GetMapping("/activityPost/{id}")
    public String detail(@PathVariable("id")Long id,Model model){
        GetActivityPostDTO getActivityPostDTO = activityPostService.getPost(id);
        ActivityFile file = getActivityPostDTO.getFile();
        model.addAttribute("post",getActivityPostDTO);
        model.addAttribute("file",file);
        return "Activity/activityDetail.html";
    }
    @GetMapping("/activityPost/attach/{id}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long id) throws MalformedURLException {
        ActivityFile file = activityFileRepository.findById(id).orElse(null);

        UrlResource resource = new UrlResource("file:"+file.getSavedPath());

        String encodedFileName = UriUtils.encode(file.getOrgNm(), StandardCharsets.UTF_8);

        String contentDisposition = "attachment; filename=\""+encodedFileName+"\"";

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,contentDisposition).body(resource);

    }

    @GetMapping("/activityPost/delete/{id}")
    public String deletePost(@PathVariable Long id){
        activityPostService.deletePost(id);
        return "redirect:/activity";

    }

    @GetMapping("/activityPost/edit/{id}")
    public String editPage(@PathVariable Long id, Model model){
        GetActivityPostDTO post = activityPostService.getPost(id);
        model.addAttribute("post",post);
        return "Activity/activityEdit.html";
    }

    @PostMapping("/activityPost/edit/{id}")
    public String editPost(@PathVariable Long id, ActivityPostDTO activityPostDTO) throws IOException{
        activityPostService.editPost(id,activityPostDTO);
        return "redirect:/activity";
    }
}
