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
//@Transactional
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 Login 성공!");
        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

            // User의 Role이 GUEST일 경우 처음 요청한 회원이므로 회원가입 페이지로 리다이렉트
            if(oAuth2User.getAuthority()==(Authority.GUEST)) {

//                String accessToken = jwtService.createAccessToken(oAuth2User.getEmail());
//                response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
//                response.sendRedirect("https://geport.blog"); // 프론트의 회원가입 추가 정보 입력 폼으로 리다이렉트
//
//                jwtService.sendAccessAndRefreshToken(response, accessToken, null);
//                Member findUser = userRepository.findByEmail(oAuth2User.getEmail())
//                                .orElseThrow(() -> new IllegalArgumentException("이메일에 해당하는 유저가 없습니다."));
//                findUser.authorizeUser();

                String accessToken = jwtService.createAccessToken(oAuth2User.getEmail());
                response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
                log.info("accessToken: "+ accessToken);

                setCookieMemberId(response, oAuth2User);
//                return ResponseEntity.ok("success");
                response.sendRedirect("http://geport.blog"); // 프론트의 회원가입 추가 정보 입력 폼으로 리다이렉트

//                jwtService.sendAccessAndRefreshToken(response, accessToken, null);
                Member findUser = userRepository.findByEmail(oAuth2User.getEmail())
                        .orElseThrow(() -> new IllegalArgumentException("이메일에 해당하는 유저가 없습니다."));

//                findUser.authorizeUser();
//                userRepository.save(findUser);
                log.info(findUser.getAuthority().toString());
                log.info(" GUEST 회원가입 성공");


            } else {
                loginSuccess(response, oAuth2User); // 로그인에 성공한 경우 access, refresh 토큰 생성
                response.sendRedirect("http://geport.blog"); // 메인페이지
                log.info("로그인 성공");

            }
        } catch (Exception e) {
            throw e;
        }

    }

    private void setCookieMemberId(HttpServletResponse response, CustomOAuth2User oAuth2User) {
        String memberId = String.valueOf(userRepository.findByEmail(oAuth2User.getEmail()).get().getId());
        String encodedMemberId = URLEncoder.encode(memberId, StandardCharsets.UTF_8);
        Cookie idCookie = new Cookie("memberId", encodedMemberId);
        idCookie.setHttpOnly(true);
        idCookie.setSecure(true);
        idCookie.setPath("/");
        idCookie.setAttribute("SameSite", "None");
        response.addCookie(idCookie);
    }


    private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException {
        String accessToken = jwtService.createAccessToken(oAuth2User.getEmail());
        log.info("accessToken: "+ accessToken);

        String refreshToken = jwtService.createRefreshToken();
        response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);

        jwtService.setCookieRefreshToken(response, refreshToken);
        jwtService.updateRefreshToken(oAuth2User.getEmail(), refreshToken);
    }

}
