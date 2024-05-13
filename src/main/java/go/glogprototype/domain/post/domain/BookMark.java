package go.glogprototype.domain.post.domain;

import go.glogprototype.domain.user.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter

public class BookMark {

    @Id @GeneratedValue
    @Column(name = "bookmark_id")
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;



}
