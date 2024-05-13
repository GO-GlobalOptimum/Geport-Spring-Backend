package go.glogprototype.domain.user.dao;

import go.glogprototype.domain.user.domain.MessageToAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageToAdminRepository extends JpaRepository<MessageToAdmin,Long> {
}
