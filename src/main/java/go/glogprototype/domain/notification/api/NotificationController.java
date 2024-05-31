package go.glogprototype.domain.notification.api;

import go.glogprototype.domain.notification.application.NotificationService;
import go.glogprototype.domain.user.dao.UserRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("spring/notification")
public class NotificationController {
    //수정1
    private final NotificationService notificationService;
    private final UserRepository userRepository;

    @GetMapping("/subscribe")
    public SseEmitter subscribe(@AuthenticationPrincipal UserDetails userDetails) {
        String userEmail = userDetails.getUsername(); // UserDetails에서 userId 추출하는 메소드
        Long userId = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("No member found with email: " + userEmail))
                .getId(); // 이메일에 해당하는 멤버의 ID 추출

        return notificationService.subscribe(userId);
    }
}
