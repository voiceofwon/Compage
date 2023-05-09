package CompWeb.Homepage.Service;


import CompWeb.Homepage.Entity.NoticeFile;
import CompWeb.Homepage.Entity.TopFixedFile;
import CompWeb.Homepage.Repository.NoticeFileRepository;
import CompWeb.Homepage.Repository.TopFixedFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TopFixedFileService {
    @Value("${file.dir}")
    private String fileDir;

    private final TopFixedFileRepository topFixedFileRepository;

    public TopFixedFile toFileEntity(MultipartFile files) throws IOException {
        if(files.isEmpty()){
            return null;
        }

        String origName = files.getOriginalFilename();

        String uuid = UUID.randomUUID().toString();

        String extension = origName.substring(origName.lastIndexOf("."));

        String savedName = uuid + extension;

        String savedPath = fileDir+savedName;

        TopFixedFile file = TopFixedFile.builder()
                .orgNm(origName)
                .savedNm(savedName)
                .savedPath(savedPath)
                .build();

        return file;
    }

    public TopFixedFile saveFile(MultipartFile files) throws IOException{
        if(!(files.getSize()>0)){
            return null;
        }

        String origName = files.getOriginalFilename();

        String uuid = UUID.randomUUID().toString();

        String extension = origName.substring(origName.lastIndexOf("."));

        String savedName = uuid + extension;

        String savedPath = fileDir+savedName;

        TopFixedFile file = TopFixedFile.builder()
                .orgNm(origName)
                .savedNm(savedName)
                .savedPath(savedPath)
                .build();

        files.transferTo(new java.io.File(savedPath));

        /*        NoticeFile savedFile = noticefileRepository.save(file);*/

        return file;
    }

    @Transactional
    public ResponseEntity<Resource> downloadAttach(Long id) throws MalformedURLException {
        TopFixedFile file = topFixedFileRepository.findById(id).orElse(null);

        UrlResource resource = new UrlResource("file:"+file.getSavedPath());

        String encodedFileName = UriUtils.encode(file.getOrgNm(), StandardCharsets.UTF_8);

        String contentDisposition = "attachment; filename=\""+encodedFileName + "\"";

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,contentDisposition).body(resource);
    }
}
