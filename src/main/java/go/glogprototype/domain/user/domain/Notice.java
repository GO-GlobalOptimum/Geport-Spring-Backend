package go.glogprototype.domain.user.domain;

import go.glogprototype.domain.post.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter

public class Notice extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "notice_id")
    private Long id;

    private String title;

    private String content;

    @ManyToOne
    private Member member;
}
