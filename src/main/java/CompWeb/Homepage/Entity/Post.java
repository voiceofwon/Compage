package CompWeb.Homepage.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter@Setter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
//게시글 Entity의 공통부분을 재작성 하지 않기 위한 abstract class
public abstract class Post {
    @Column(length = 25, nullable = false)
    private String author;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String content;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
