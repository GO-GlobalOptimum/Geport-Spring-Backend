package go.glogprototype.domain.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
//@Table(name = "interest_tag")
public class InterestTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interest_tag_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
