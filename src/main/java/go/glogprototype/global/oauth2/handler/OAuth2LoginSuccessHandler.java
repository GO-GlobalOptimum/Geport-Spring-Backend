package go.glogprototype.global.oauth2.handler;

import go.glogprototype.domain.user.dao.UserRepository;
import go.glogprototype.domain.user.domain.Authority;
import go.glogprototype.domain.user.domain.Member;
import go.glogprototype.global.jwt.service.JwtService;
import go.glogprototype.global.oauth2.CustomOAuth2User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 Login 성공!");
        //태스트
        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

            // User의 Role이 GUEST일 경우 처음 요청한 회원이므로 회원가입 페이지로 리다이렉트
            if (oAuth2User.getAuthority() == Authority.GUEST) {
                handleGuestUser(response, oAuth2User);
            } else {
                handleAuthenticatedUser(response, oAuth2User);
            }
        } catch (Exception e) {
            log.error("Authentication Success Handler Error", e);
            throw e;
        }
    }

    private void handleGuestUser(HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException {
        String accessToken = jwtService.createAccessToken(oAuth2User.getEmail());
        response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
        log.info("accessToken: " + accessToken);

        setCookieMemberId(response, oAuth2User);

        Member findUser = userRepository.findByEmail(oAuth2User.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("이메일에 해당하는 유저가 없습니다."));
        
        log.info(findUser.getAuthority().toString());
        log.info("GUEST 회원가입 성공");

        // 회원가입 페이지로 리다이렉트
        response.sendRedirect("http://localhost:3000/main");
    }

    private void handleAuthenticatedUser(HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException {
        loginSuccess(response, oAuth2User); // 로그인에 성공한 경우 access, refresh 토큰 생성
        setCookieMemberId(response,oAuth2User);
        response.sendRedirect("http://localhost:3000/main"); // 메인페이지
        
        log.info("로그인 성공");
    }

    private void setCookieMemberId(HttpServletResponse response, CustomOAuth2User oAuth2User) {
        String memberId = String.valueOf(userRepository.findByEmail(oAuth2User.getEmail()).get().getId());
        String encodedMemberId = URLEncoder.encode(memberId, StandardCharsets.UTF_8);
        Cookie idCookie = new Cookie("memberId", encodedMemberId);
        idCookie.setHttpOnly(true);
        idCookie.setSecure(true);
        idCookie.setPath("/");
        idCookie.setMaxAge(1000 * 60 * 6);
        idCookie.setAttribute("SameSite", "None");
        response.addCookie(idCookie);
    }

    private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException {
        String accessToken = jwtService.createAccessToken(oAuth2User.getEmail());
        log.info("accessToken: " + accessToken);

        String refreshToken = jwtService.createRefreshToken();
        response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);

        jwtService.setCookieRefreshToken(response, refreshToken);
        jwtService.updateRefreshToken(oAuth2User.getEmail(), refreshToken);
    }
}
