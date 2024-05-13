package go.glogprototype.domain.post.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
@Table(name = "category_post")
public class CategoryPost {

    @Id @GeneratedValue
    @Column(name = "category_post_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "category-id")
    private Category category;
}
