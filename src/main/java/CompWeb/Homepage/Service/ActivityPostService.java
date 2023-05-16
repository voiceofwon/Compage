package CompWeb.Homepage.Service;

import CompWeb.Homepage.DTO.ActivityPostDTO;
import CompWeb.Homepage.DTO.GetActivityPostDTO;
import CompWeb.Homepage.DTO.GetPostDTO;
import CompWeb.Homepage.DTO.SosPostDTO;
import CompWeb.Homepage.Entity.ActivityFile;
import CompWeb.Homepage.Entity.ActivityPost;
import CompWeb.Homepage.Entity.File;
import CompWeb.Homepage.Entity.SosPost;
import CompWeb.Homepage.Repository.ActivityPostRepository;
import CompWeb.Homepage.Repository.SosPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityPostService {
    private final ActivityPostRepository activityPostRepository;

    private final ActivityFileService activityfileService;

    @Transactional
    public Long savePost(ActivityPostDTO activityPostDTO) throws IOException {


        ActivityPost activityPost = activityPostDTO.toEntity();


        ActivityFile file = activityfileService.saveFile(activityPostDTO.getMultipartFile());
        if (file != null) {
            activityPost.setFile(file);
            file.setActivityPost(activityPost);
        } else {
            file = ActivityFile.builder()
                    .savedPath("null")
                    .savedNm("null")
                    .orgNm("첨부된 파일이 없습니다.")
                    .build();

            activityPost.setFile(file);
            file.setActivityPost(activityPost);

        }


        activityPostRepository.save(activityPost);


        return activityPost.getId();
    }

    @Transactional
    public List<ActivityPostDTO> getPostList() {
        List<ActivityPost> postList = activityPostRepository.findAllJoinFetch();
        List<ActivityPostDTO> postDtoList = new ArrayList<>();

        for (ActivityPost activityPost : postList) {
            ActivityPostDTO activityPostDTO = ActivityPostDTO.builder()
                    .id(activityPost.getId())
                    .author(activityPost.getAuthor())
                    .title(activityPost.getTitle())
                    .content(activityPost.getContent())
                    .createdDate(activityPost.getCreatedDate())
                    .build();
            postDtoList.add(activityPostDTO);

        }
        Collections.reverse(postDtoList);
        return postDtoList;
    }

    @Transactional
    public GetActivityPostDTO getPost(Long id) {
        ActivityPost activityPost = activityPostRepository.findById(id).get();

        GetActivityPostDTO getActivityPostDTO = GetActivityPostDTO.builder()
                .id(activityPost.getId())
                .author(activityPost.getAuthor())
                .title(activityPost.getTitle())
                .content(activityPost.getContent())
                .createdDate(activityPost.getCreatedDate())
                .file(activityPost.getFile())
                .build();

        return getActivityPostDTO;
    }

    @Transactional
    public Long deletePost(Long id) {
        ActivityPost activityPost = activityPostRepository.findById(id).orElse(null);
        activityPostRepository.delete(activityPost);

        return activityPost.getId();
    }

    @Transactional
    public Long editPost(Long id, ActivityPostDTO activityPostDTO) throws IOException{
        ActivityPost savedPost = activityPostRepository.findById(id)
                .orElseThrow(()->new IOException("["+activityPostDTO.getTitle()+"] Not Found"));
        savedPost.setTitle(activityPostDTO.getTitle());
        savedPost.setAuthor(activityPostDTO.getAuthor());
        savedPost.setContent(activityPostDTO.getContent());

        activityPostRepository.save(savedPost);

        return savedPost.getId();
    }
}
