package CompWeb.Homepage.Service;


import CompWeb.Homepage.DTO.TopFixedPostDTO;
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

    @Transactional
    public Long savePost(TopFixedPostDTO topFixedPostDTO) throws IOException {

        TopFixedPost topFixedPost = topFixedPostDTO.toEntity();

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
    public TopFixedPostDTO getPost(Long id){
        TopFixedPost topFixedPost = topFixedPostRepository.findById(id).get();

        TopFixedPostDTO topFixedPostDTO = TopFixedPostDTO.builder()
                .id(topFixedPost.getId())
                .author(topFixedPost.getAuthor())
                .title(topFixedPost.getTitle())
                .content(topFixedPost.getContent())
                .createdDate(topFixedPost.getCreatedDate())
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
