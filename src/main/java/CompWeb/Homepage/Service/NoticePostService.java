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
import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
public class NoticePostService {

    private final NoticeFileService noticeFileService;

    private final NoticePostRepository noticePostRepository;

    @Transactional
    public Long savePost(NoticePostDTO noticePostDTO) throws IOException {

        NoticePost noticePost = noticePostDTO.toEntity();

        NoticeFile file = noticeFileService.saveFile(noticePostDTO.getMultipartFile());
        if(file != null){
            noticePost.setNoticeFile(file);
            file.setNoticePost(noticePost);
        }else{
            file = NoticeFile.builder()
                    .savedNm("null")
                    .savedPath("null")
                    .orgNm("첨부된 파일이 없습니다.")
                    .build();
            noticePost.setNoticeFile(file);
            file.setNoticePost(noticePost);

        }



        noticePostRepository.save(noticePost);


        return noticePost.getId();
    }

    @Transactional
    public List<NoticePostDTO> getPostList() {
        List<NoticePost> postList = noticePostRepository.findAllJoinFetch();
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
        Collections.reverse(postDtoList);
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
    public Long editPost(Long id, NoticePostDTO noticePostDTO) throws IOException{
        NoticePost savedPost = noticePostRepository.findById(id)
                .orElseThrow(()->new IOException("["+noticePostDTO.getTitle()+"] Not Found"));
        savedPost.setTitle(noticePostDTO.getTitle());
        savedPost.setAuthor(noticePostDTO.getAuthor());
        savedPost.setContent(noticePostDTO.getContent());

        noticePostRepository.save(savedPost);

        return savedPost.getId();
    }
}
