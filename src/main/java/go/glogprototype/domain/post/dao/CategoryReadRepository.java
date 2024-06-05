package go.glogprototype.domain.post.dao;

import go.glogprototype.domain.post.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryReadRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
