package go.glogprototype.domain.user.domain;

import go.glogprototype.domain.post.domain.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
public class View {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "view_id")
    private Long id;

    private int view_count;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "FK_VIEW_POST"))
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "FK_VIEW_MEMBER"))
    private Member member;

}
