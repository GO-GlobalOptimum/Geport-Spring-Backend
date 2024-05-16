package go.glogprototype.domain.user.domain;

import go.glogprototype.domain.post.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@RequiredArgsConstructor
public class Follow extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "follow_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private Member follower;


    @ManyToOne
    @JoinColumn(name = "following_id")
    private Member following;


}
