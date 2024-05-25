package go.glogprototype.domain.user.domain;

import go.glogprototype.domain.post.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor

public class Faq extends BaseEntity {
    @Id
    @Column(name = "faq_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "longtext")
    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
