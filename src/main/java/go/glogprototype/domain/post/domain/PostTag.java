package go.glogprototype.domain.post.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@RequiredArgsConstructor
//@Table(name = "post_tag"
////        , uniqueConstraints = {@UniqueConstraint(columnNames = {"post_id", "contents"})}
//)
public class PostTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_tag_id")
    private Long id;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private Boolean is_user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public PostTag(Long id, String contents, Boolean is_user, Post post) {
        this.id = id;
        this.contents = contents;
        this.is_user = is_user;
        this.post = post;
    }
}

//asdf