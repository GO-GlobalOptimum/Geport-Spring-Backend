package go.glogprototype.domain.post.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
public class Comment {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String comment_content;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
