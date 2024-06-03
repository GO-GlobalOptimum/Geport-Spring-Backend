package go.glogprototype.domain.user.domain;

import go.glogprototype.domain.post.domain.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class View {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "view_id")
    private Long id;

    private int view_count = 0;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "FK_VIEW_POST"))
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "FK_VIEW_MEMBER"))
    private Member member;

    @Column(name = "updated_time")
    private LocalDateTime updatedTime;

    public View(Post post, Member member) {
        this.post = post;
        this.member = member;
        this.view_count = 1; // 새로 생성된 View의 초기 조회수는 1
        this.updatedTime = LocalDateTime.now(); // 초기 생성 시 현재 시간으로 설정
    }

    public void incrementViewCount() {
        this.view_count++;
        this.updatedTime = LocalDateTime.now(); // 조회수 증가 시 현재 시간으로 업데이트
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedTime = LocalDateTime.now();
    }
}
