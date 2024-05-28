package go.glogprototype.global.oauth2.handler;

import go.glogprototype.domain.user.dao.MemberRepository;
import go.glogprototype.domain.user.domain.Authority;
import go.glogprototype.domain.user.domain.Member;
import go.glogprototype.global.jwt.service.JwtService;
import go.glogprototype.global.oauth2.CustomOAuth2User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final MemberRepository userRepository;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 Login 성공!");
        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

            Optional<Member> optionalMember = userRepository.findByEmail(oAuth2User.getEmail());
            if (optionalMember.isPresent()) {
                Member user = optionalMember.get();

                // User의 Role이 GUEST일 경우 처음 요청한 회원이므로 회원가입 페이지로 리다이렉트
                if (user.getAuthority() == Authority.GUEST) {
                    log.info("OAuth2 유저 정보 : " + user.getAuthority());

                    String accessToken = jwtService.createAccessToken(user.getEmail());
                    log.info("Bearer " + accessToken);
                    String refreshToken = jwtService.createRefreshToken();

                    user.updateRefreshToken(refreshToken);
                    userRepository.save(user);

                    jwtService.sendAccessAndRefreshToken(response, accessToken, null);

                    user.authorizeUser();
                    userRepository.save(user);

                    response.sendRedirect("http://localhost:8080/auth2/code/google"); // 프론트의 회원가입 추가 정보 입력 폼으로 리다이렉트
                } else {
                    loginSuccess(response, user); // 로그인에 성공한 경우 access, refresh 토큰 생성
                }
            } else {
                throw new IllegalStateException("No user found with email: " + oAuth2User.getEmail());
            }
        } catch (Exception e) {
            throw e;
        }
    }

    // 로그인 성공 시 처리 로직
    private void loginSuccess(HttpServletResponse response, Member user) throws IOException {
        String accessToken = jwtService.createAccessToken(user.getEmail());
        String refreshToken = jwtService.createRefreshToken();
        log.info("Bearer " + accessToken);

        jwtService.updateRefreshToken(user.getEmail(), refreshToken);
        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);

        response.sendRedirect("http://geport.blog"); // 로그인 성공 시 메인 페이지로 리다이렉트
    }
}
