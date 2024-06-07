package go.glogprototype.domain.post.dao;

import go.glogprototype.domain.post.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Locale;

public interface CategoryWriteRepository extends JpaRepository<Category,Long> {
}
