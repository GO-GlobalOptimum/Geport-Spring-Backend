package go.glogprototype.global.jwt.api;

import go.glogprototype.global.jwt.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import go.glogprototype.domain.user.dao.MemberRepository;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/spring/auth")
public class AuthenticationController {

    @Autowired
    private JwtService jwtService;

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshAccessToken(@RequestParam String refreshToken) {
        try {
            String newAccessToken = jwtService.refreshAccessToken(refreshToken)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));
            return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }
}



