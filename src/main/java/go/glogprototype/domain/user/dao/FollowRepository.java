package go.glogprototype.domain.user.dao;

import go.glogprototype.domain.user.domain.Follow;
import go.glogprototype.domain.user.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByFollowerAndFollowing(Member follower, Member following);
    void deleteByFollowerAndFollowing(Member follower, Member following);
}
