package go.glogprototype.domain.user.api;

import go.glogprototype.domain.user.application.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spring/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/follow")
    public ResponseEntity<String> followUser(@RequestParam String followingEmail, @AuthenticationPrincipal UserDetails userDetails) {
        String followerEmail = userDetails.getUsername();
        followService.followUser(followerEmail, followingEmail);
        return ResponseEntity.ok("Followed successfully");
    }

    @PostMapping("/unfollow")
    public ResponseEntity<String> unfollowUser(@RequestParam String followingEmail, @AuthenticationPrincipal UserDetails userDetails) {
        String followerEmail = userDetails.getUsername();
        followService.unfollowUser(followerEmail, followingEmail);
        return ResponseEntity.ok("Unfollowed successfully");
    }
}
