package go.glogprototype.domain.post.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@RequiredArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String name;

//    @ManyToMany(mappedBy = "categories")
//    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "category")
    private List<CategoryPost> categoryPostList = new ArrayList<>();

    @Builder
    public Category(String name) {
        this.name = name;
    }

}
