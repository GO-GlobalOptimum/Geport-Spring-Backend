package go.glogprototype.domain.user.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter

public class Notice extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    private String title;

    @Column(columnDefinition = "longtext")
    private String content;

    @ManyToOne
    private Member member;
}
