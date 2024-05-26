package go.glogprototype.domain.user.dao;

import go.glogprototype.domain.user.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByEmail(String email);

    Optional<Member> findByNickName(String nickname);

    Optional<Member> findByRefreshToken(String refreshToken);

    Optional<Member> findBySocialId(String socialId);

}
