package go.glogprototype.domain.post.dao;

import go.glogprototype.domain.post.domain.Category;
import go.glogprototype.domain.post.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

}