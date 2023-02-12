package CompWeb.Homepage.Service;

import CompWeb.Homepage.DTO.GetPostDTO;
import CompWeb.Homepage.DTO.SosPostDTO;
import CompWeb.Homepage.Entity.File;
import CompWeb.Homepage.Entity.SosPost;
import CompWeb.Homepage.Repository.FileRepository;
import CompWeb.Homepage.Repository.SosPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SosPostService {
    private final SosPostRepository sosPostRepository;

    private final FileRepository fileRepository;

    private final FileService fileService;

    @Transactional
    public Long savePost(SosPostDTO sosPostDTO) throws IOException {

        File file = null;
        SosPost sosPost = sosPostDTO.toEntity();
        if(sosPostDTO.getMultipartFile().getSize() >0){
            file = fileService.toFileEntity(sosPostDTO.getMultipartFile());
        }

        if(file != null){
            file = fileService.saveFile(sosPostDTO.getMultipartFile());
            sosPost.setFile(file);

            file.setSosPost(sosPost);
            System.out.println(sosPostDTO.getMultipartFile());
            System.out.println(file);

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
    public Long editPost(Long id, SosPostDTO sosPostDTO){
        SosPost savedPost = sosPostRepository.findById(id).orElse(null);
        savedPost.setTitle(sosPostDTO.getTitle());
        savedPost.setAuthor(sosPostDTO.getAuthor());
        savedPost.setContent(sosPostDTO.getContent());

        sosPostRepository.save(savedPost);

        return savedPost.getId();
    }
}
