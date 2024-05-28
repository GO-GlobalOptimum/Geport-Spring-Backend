package go.glogprototype.domain.post.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "post_tag")
public class PostTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="post_tag_id")
    private Long id;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private Boolean is_user;

    @ManyToOne
    @JoinColumn(name = "post_id") // unique 속성을 사용하여 고유 제약 조건 설정
    private Post post;

    public PostTag(String tags, boolean is_user) {
        this.contents = tags;
        this.is_user = is_user;
    }
}
