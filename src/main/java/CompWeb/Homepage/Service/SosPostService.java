package CompWeb.Homepage.Service;

import CompWeb.Homepage.DTO.GetPostDTO;
import CompWeb.Homepage.DTO.SosPostDTO;
import CompWeb.Homepage.Entity.File;
import CompWeb.Homepage.Entity.SosPost;
import CompWeb.Homepage.Repository.FileRepository;
import CompWeb.Homepage.Repository.SosPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SosPostService {
    private final SosPostRepository sosPostRepository;

    private final FileService fileService;

    @Transactional
    public Long savePost(SosPostDTO sosPostDTO) throws IOException {


        SosPost sosPost = sosPostDTO.toEntity();



        File file = fileService.saveFile(sosPostDTO.getMultipartFile());
        if(file != null) {
            sosPost.setFile(file);
            file.setSosPost(sosPost);
        }else{
            file = File.builder()
                    .savedPath("null")
                    .savedNm("null")
                    .orgNm("첨부된 파일이 없습니다.")
                    .build();

            sosPost.setFile(file);
            file.setSosPost(sosPost);

        }



        sosPostRepository.save(sosPost);


        return sosPost.getId();
    }

    @Transactional
    public List<SosPostDTO> getPostList() {
        List<SosPost> postList = sosPostRepository.findAll();
        List<SosPostDTO> postDtoList = new ArrayList<>();

        for (SosPost sosPost : postList) {
            SosPostDTO sosPostDTO = SosPostDTO.builder()
                    .id(sosPost.getId())
                    .author(sosPost.getAuthor())
                    .title(sosPost.getTitle())
                    .content(sosPost.getContent())
                    .createdDate(sosPost.getCreatedDate())
                    .build();
            postDtoList.add(sosPostDTO);

        }
        Collections.reverse(postDtoList);
        return postDtoList;
    }

    @Transactional
    public GetPostDTO getPost(Long id) {
        SosPost sosPost = sosPostRepository.findById(id).get();

        GetPostDTO getPostDTO = GetPostDTO.builder()
                .id(sosPost.getId())
                .author(sosPost.getAuthor())
                .title(sosPost.getTitle())
                .content(sosPost.getContent())
                .createdDate(sosPost.getCreatedDate())
                .file(sosPost.getFile())
                .build();

        return getPostDTO;
    }

    @Transactional
    public Long deletePost(Long id) {
        SosPost sosPost = sosPostRepository.findById(id).orElse(null);
        sosPostRepository.delete(sosPost);

        return sosPost.getId();
    }

    @Transactional
    public Long editPost(Long id, SosPostDTO sosPostDTO) throws IOException{
        SosPost savedPost = sosPostRepository.findById(id)
                .orElseThrow(()->new IOException("["+sosPostDTO.getTitle()+"] Not Found"));
        savedPost.setTitle(sosPostDTO.getTitle());
        savedPost.setAuthor(sosPostDTO.getAuthor());
        savedPost.setContent(sosPostDTO.getContent());

        /*File file = fileService.saveFile(sosPostDTO.getMultipartFile());
        if(file != null) {
            savedPost.setFile(file);
            file.setSosPost(savedPost);
        }else{
            file = File.builder()
                    .savedPath("null")
                    .savedNm("null")
                    .orgNm("첨부된 파일이 없습니다.")
                    .build();

            savedPost.setFile(file);
            file.setSosPost(savedPost);

        }*/

        sosPostRepository.save(savedPost);

        return savedPost.getId();
    }
}
