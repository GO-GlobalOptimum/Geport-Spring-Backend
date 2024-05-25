package go.glogprototype.domain.post.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
@Table(name = "post_tag")
public class PostTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="post_tag_id")
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
