package go.glogprototype.domain.user.application;

import go.glogprototype.domain.user.dao.FollowRepository;
import go.glogprototype.domain.user.dao.UserRepository;
import go.glogprototype.domain.user.domain.Follow;
import go.glogprototype.domain.user.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowService {

    private final UserRepository memberRepository;
    private final FollowRepository followRepository;

    public void followUser(String followerEmail, String followingEmail) {
        Member follower = memberRepository.findByEmail(followerEmail)
                .orElseThrow(() -> new IllegalArgumentException("No member found with email: " + followerEmail));
        Member following = memberRepository.findByEmail(followingEmail)
                .orElseThrow(() -> new IllegalArgumentException("No member found with email: " + followingEmail));

        if (!followRepository.existsByFollowerAndFollowing(follower, following)) {
            Follow follow = Follow.builder()
                    .follower(follower)
                    .following(following)
                    .build();
            followRepository.save(follow);

            follower.setFollowingCount(follower.getFollowingCount() + 1);
            following.setFollowerCount(following.getFollowerCount() + 1);
            memberRepository.save(follower);
            memberRepository.save(following);
        }
    }

    public void unfollowUser(String followerEmail, String followingEmail) {
        Member follower = memberRepository.findByEmail(followerEmail)
                .orElseThrow(() -> new IllegalArgumentException("No member found with email: " + followerEmail));
        Member following = memberRepository.findByEmail(followingEmail)
                .orElseThrow(() -> new IllegalArgumentException("No member found with email: " + followingEmail));

        if (followRepository.existsByFollowerAndFollowing(follower, following)) {
            followRepository.deleteByFollowerAndFollowing(follower, following);

            follower.setFollowingCount(follower.getFollowingCount() - 1);
            following.setFollowerCount(following.getFollowerCount() - 1);
            memberRepository.save(follower);
            memberRepository.save(following);
        }
    }
}
