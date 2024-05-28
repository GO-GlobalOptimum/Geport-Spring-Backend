package go.glogprototype.domain.post.domain;

import go.glogprototype.domain.user.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
public class Recomment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recomment_id")
    private Long id;

    @Column(columnDefinition = "longtext")
    private String recomment_content;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;  // 대댓글이 달린 원본 댓글

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;  // 대댓글 작성자

    // 생성자, getter 및 setter 등 기타 필요한 메서드
}
