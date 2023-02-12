package CompWeb.Homepage.Service;

import CompWeb.Homepage.DTO.GetNoticePostDTO;
import CompWeb.Homepage.DTO.GetPostDTO;
import CompWeb.Homepage.DTO.NoticePostDTO;
import CompWeb.Homepage.DTO.SosPostDTO;
import CompWeb.Homepage.Entity.File;
import CompWeb.Homepage.Entity.NoticeFile;
import CompWeb.Homepage.Entity.NoticePost;
import CompWeb.Homepage.Entity.SosPost;
import CompWeb.Homepage.Repository.NoticePostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class NoticePostService {

    private final NoticeFileService noticeFileService;

    private final NoticePostRepository noticePostRepository;

    @Transactional
    public Long savePost(NoticePostDTO noticePostDTO) throws IOException {

        NoticePost noticePost = noticePostDTO.toEntity();
        NoticeFile file = noticeFileService.toFileEntity(noticePostDTO.getMultipartFile());
        noticePost.setNoticeFile(file);
        file.setNoticePost(noticePost);
        noticeFileService.saveFile(noticePostDTO.getMultipartFile());

        noticePostRepository.save(noticePost);


        return noticePost.getId();
    }

    @Transactional
    public List<NoticePostDTO> getPostList() {
        List<NoticePost> postList = noticePostRepository.findAll();
        List<NoticePostDTO> postDtoList = new ArrayList<>();

        for (NoticePost noticePost : postList) {
            NoticePostDTO noticePostDTO = NoticePostDTO.builder()
                    .id(noticePost.getId())
                    .author(noticePost.getAuthor())
                    .title(noticePost.getTitle())
                    .content(noticePost.getContent())
                    .createdDate(noticePost.getCreatedDate())
                    .build();
            postDtoList.add(noticePostDTO);

        }
        return postDtoList;
    }

    @Transactional
    public GetNoticePostDTO getPost(Long id) {
        NoticePost noticePost = noticePostRepository.findById(id).get();

        GetNoticePostDTO getNoticePostDTO = GetNoticePostDTO.builder()
                .id(noticePost.getId())
                .author(noticePost.getAuthor())
                .title(noticePost.getTitle())
                .content(noticePost.getContent())
                .createdDate(noticePost.getCreatedDate())
                .file(noticePost.getNoticeFile())
                .build();

        return getNoticePostDTO;
    }

    @Transactional
    public Long deletePost(Long id) {
        NoticePost noticePost = noticePostRepository.findById(id).orElse(null);
        noticePostRepository.delete(noticePost);

        return noticePost.getId();
    }

    @Transactional
    public Long editPost(Long id, NoticePostDTO noticePostDTO){
        NoticePost savedPost = noticePostRepository.findById(id).orElse(null);
        savedPost.setTitle(noticePostDTO.getTitle());
        savedPost.setAuthor(noticePostDTO.getAuthor());
        savedPost.setContent(noticePostDTO.getContent());

        noticePostRepository.save(savedPost);

        return savedPost.getId();
    }
}
