package CompWeb.Homepage.Service;


import CompWeb.Homepage.DTO.GetTopFixedPostDTO;
import CompWeb.Homepage.DTO.TopFixedPostDTO;
import CompWeb.Homepage.Entity.File;
import CompWeb.Homepage.Entity.TopFixedFile;
import CompWeb.Homepage.Entity.TopFixedPost;
import CompWeb.Homepage.Repository.TopFixedPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TopFixedPostService {

    private final TopFixedPostRepository topFixedPostRepository;

    private final TopFixedFileService topFixedFileService;
    @Transactional
    public Long savePost(TopFixedPostDTO topFixedPostDTO) throws IOException {

        TopFixedPost topFixedPost = topFixedPostDTO.toEntity();

        TopFixedFile file = topFixedFileService.saveFile(topFixedPostDTO.getMultipartFile());
        if(file != null) {
            topFixedPost.setTopFixedFile(file);
            file.setTopFixedPost(topFixedPost);
        }else{
            file = TopFixedFile.builder()
                    .savedPath("null")
                    .savedNm("null")
                    .orgNm("첨부된 파일이 없습니다.")
                    .build();

            topFixedPost.setTopFixedFile(file);
            file.setTopFixedPost(topFixedPost);

        }

        topFixedPostRepository.save(topFixedPost);

        return topFixedPost.getId();
    }

    @Transactional
    public List<TopFixedPostDTO> getPostList(){
        List<TopFixedPost> postList = topFixedPostRepository.findAll();
        List<TopFixedPostDTO> postDtoList = new ArrayList<>();

        for(TopFixedPost topFixedPost : postList){
            TopFixedPostDTO topFixedPostDTO = TopFixedPostDTO.builder()
                    .id(topFixedPost.getId())
                    .author(topFixedPost.getAuthor())
                    .title(topFixedPost.getTitle())
                    .content(topFixedPost.getContent())
                    .createdDate(topFixedPost.getCreatedDate())
                    .build();
            postDtoList.add(topFixedPostDTO);
        }

        return postDtoList;
    }

    @Transactional
    public GetTopFixedPostDTO getPost(Long id){
        TopFixedPost topFixedPost = topFixedPostRepository.findById(id).get();

        GetTopFixedPostDTO topFixedPostDTO = GetTopFixedPostDTO.builder()
                .id(topFixedPost.getId())
                .author(topFixedPost.getAuthor())
                .title(topFixedPost.getTitle())
                .content(topFixedPost.getContent())
                .createdDate(topFixedPost.getCreatedDate())
                .file(topFixedPost.getTopFixedFile())
                .build();

        return topFixedPostDTO;
    }

    @Transactional
    public Long deletePost(Long id){
        TopFixedPost topFixedPost = topFixedPostRepository.findById(id).orElse(null);
        topFixedPostRepository.delete(topFixedPost);

        return  topFixedPost.getId();
    }

    @Transactional
    public Long editPost(Long id, TopFixedPostDTO topFixedPostDTO) throws IOException{
        TopFixedPost savedPost = topFixedPostRepository.findById(id)
                .orElseThrow(()->new IOException("["+topFixedPostDTO.getTitle()+"] Not Found"));
        savedPost.setTitle(topFixedPostDTO.getTitle());
        savedPost.setAuthor(topFixedPostDTO.getAuthor());
        savedPost.setContent(topFixedPostDTO.getContent());

        topFixedPostRepository.save(savedPost);
        return savedPost.getId();
    }


}
