package go.glogprototype.domain.post.domain;

import go.glogprototype.domain.user.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Like {
    @Id @GeneratedValue
    @Column(name = "like_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

}
