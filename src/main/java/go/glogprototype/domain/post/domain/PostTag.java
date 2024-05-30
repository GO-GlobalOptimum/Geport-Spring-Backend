package go.glogprototype.domain.post.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "post_tag", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"post_id", "contents"})
})
public class PostTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_tag_id")
    private Long id;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private Boolean is_user;

    @ManyToOne
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "FK_post_tag_post_id"))
    private Post post;

    public PostTag(String tags, boolean is_user) {
        this.contents = tags;
        this.is_user = is_user;
    }
}
