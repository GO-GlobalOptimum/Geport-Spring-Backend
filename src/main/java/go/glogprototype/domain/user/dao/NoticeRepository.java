package go.glogprototype.domain.user.dao;

import go.glogprototype.domain.user.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice,Long> {
}
