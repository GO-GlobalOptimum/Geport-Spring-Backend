package go.glogprototype.domain.post.domain;

import go.glogprototype.domain.user.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Post extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;

    private int viewsCount;

    private String postContent;

    private boolean isPublic;

    private int likeCount;

    private String thumbnailText;

    private String thumbnailImage;

    private boolean isComment;

    private boolean isDelete;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post")
    private List<BookMark> bookMarks = new ArrayList<>( );

    private int comment_count;

    private int bookMarkCount;





}
