package go.glogprototype.domain.post.dao;

import go.glogprototype.domain.post.domain.BookMark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookMarkRepository  extends JpaRepository<BookMark,Long> {
}
