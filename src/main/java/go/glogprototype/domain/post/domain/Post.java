package go.glogprototype.domain.post.domain;

import go.glogprototype.domain.user.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
public class Post {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;

    private int views_count;

    private String post_content;

    private boolean is_public;

    private int like_count;

    private String thumbnail_text;

    private String thumbnail_image;

    private boolean is_comment;

    private boolean is_delete;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;


}
