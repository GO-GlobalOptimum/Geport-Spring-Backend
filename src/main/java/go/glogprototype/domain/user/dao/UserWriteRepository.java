package go.glogprototype.domain.user.dao;

import go.glogprototype.domain.user.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserWriteRepository extends JpaRepository<Member, Long> {
    // 쓰기 작업만을 처리합니다. 일반적인 쓰기 작업 (save, delete 등)은 JpaRepository에 의해 제공됩니다.
}
