package go.glogprototype.domain.user.dao;

import go.glogprototype.domain.user.domain.Faq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaqRepository extends JpaRepository<Faq,Long> {
}
