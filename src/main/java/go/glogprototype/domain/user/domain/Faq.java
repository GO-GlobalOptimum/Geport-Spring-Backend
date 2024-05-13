package go.glogprototype.domain.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor

public class Faq {
    @Id @GeneratedValue
    @Column(name = "faq_id")
    private Long id;

    private String title;

    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
