package go.glogprototype.domain.notification.api;

import go.glogprototype.domain.notification.application.NotificationService;
import go.glogprototype.domain.post.application.PostService;
import go.glogprototype.domain.post.dto.CreatePostRequestDto;
import go.glogprototype.domain.post.dto.CreatePostResponseDto;
import go.glogprototype.domain.post.dto.PostDto.*;
import go.glogprototype.domain.user.dao.MemberRepository;
import go.glogprototype.domain.user.domain.Member;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("spring/notification")
public class NotificationController {

    private final NotificationService notificationService;
    private final MemberRepository memberRepository;

    @GetMapping("/subscribe")
    public SseEmitter subscribe(@AuthenticationPrincipal UserDetails userDetails) {
        String userEmail = userDetails.getUsername(); // UserDetails에서 userId 추출하는 메소드
        Long userId = memberRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("No member found with email: " + userEmail))
                .getId(); // 이메일에 해당하는 멤버의 ID 추출

        return notificationService.subscribe(userId);
    }
}

