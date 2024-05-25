package go.glogprototype.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;


import go.glogprototype.domain.user.dao.MemberRepository;
import go.glogprototype.domain.user.domain.Member;
import go.glogprototype.global.jwt.filter.CustomJsonUsernamePasswordAuthenticationFilter;
import go.glogprototype.global.jwt.filter.JwtAuthenticationProcessingFilter;
import go.glogprototype.global.jwt.service.JwtService;
import go.glogprototype.global.oauth2.handler.OAuth2LoginFailureHandler;
import go.glogprototype.global.oauth2.handler.OAuth2LoginSuccessHandler;
import go.glogprototype.global.oauth2.service.CustomOAuth2UserService;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;


/**
 * 인증은 CustomJsonUsernamePasswordAuthenticationFilter에서 authenticate()로 인증된 사용자로 처리
 * JwtAuthenticationProcessingFilter는 AccessToken, RefreshToken 재발급
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtService jwtService;
    private final MemberRepository userRepository;
    private final ObjectMapper objectMapper;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .formLogin(formlogin->formlogin.disable()) // disable FormLogin
//                .httpBasic(httpBasic ->
//                        httpBasic.disable()
//                ) // disable httpBasic
//                .csrf(csrf->csrf.disable()) // disable csrf security
//                .headers((headersConfig) ->
//                        headersConfig.frameOptions(frameOptionsConfig ->
//                                frameOptionsConfig.disable()
//                        )
//                )
//
//                // Since session is not used, set to STATELESS
//                .sessionManagement(sessionManagement ->
//                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//
//                //== Permission management options per URL ==//
//                .authorizeHttpRequests(authorize ->
//                        authorize
////                                .requestMatchers("/", "/css/**", "/images/**", "/js/**", "/favicon.ico", "/h2-console/**").permitAll()
//                                .requestMatchers("/sign-up","/login","/swagger-ui/index.html/**",   "/api-docs",
//                                        "/swagger-ui-custom.html",
//                                        "/v3/api-docs/**",
//                                        "/swagger-ui/**",
//                                        "/api-docs/**",
//                                        "/swagger-ui.html",
//                                        "/swagger-custom-ui.html").permitAll() // Access to membership registration
//                                .anyRequest().authenticated() // Only authenticated users can access all paths other than the above
//
//
//                )

//                .authorizeRequests(authorize ->
//                        authorize
//                                .requestMatchers("/", "/css/**", "/images/**", "/js/**", "/favicon.ico", "/h2-console/**").permitAll()
//                                .requestMatchers(new AntPathRequestMatcher("/oauth2/authorization/google")).permitAll()
//                                .requestMatchers(new AntPathRequestMatcher("/sign-up")).permitAll()// Access to membership registration
//                                .anyRequest().authenticated() // Only authenticated users can access all paths other than the above
//
//
//                )
        http
                .cors(withDefaults())
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .rememberMe(AbstractHttpConfigurer::disable)
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(new AntPathRequestMatcher("/oauth2/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/")).permitAll()  // 루트 경로 허용
                        .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll() // Swagger UI 경로 허용
                        .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**")).permitAll() // Swagger API docs 경로 허용
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2Login -> oauth2Login
                        .authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint
                                .baseUri("/oauth2/authorization/google") //여기로 인증 요청이 들어옴. OAuth2 인증 요청이 시작되는 엔드포인트, Google 로그인 버튼을 눌렀을 때 이 엔드포인트로 이동
                                //.authorizationRequestRepository(cookieAuthorizationRequestRepository)
                        )
                        .redirectionEndpoint(redirectionEndpoint -> redirectionEndpoint
                                .baseUri("/oauth2/code/google") //authorization url을 통한 인증 결과에 따라 redirect 되는 url. 인증이 정상적으로 진행되는 경우에는 callback url으로 사용자 인증 코드(authorization code)를 함께 반환됨.
                        )
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                                        .userService(customOAuth2UserService) //내부적으로 authorization code를 access token으로 교환하는 과정을 거쳐 CustomOAuth2UserService로 넘어온다.
                                //CutomOAuth2UserService에서는 access token을 통해 Resource Server에 해당 사용자의 필요한 정보를 요청
                                //해당 정보를 통해 회원가입 또는 회원 정보 갱신 로직이 진행되며, 최종적으로 security가 인증 여부를 확인할 수 있도록 OAuth2User 객체를 반환
                        )
                        .successHandler(oAuth2LoginSuccessHandler) //마지막 과정으로 oAuth2AuthenticationSuccessHandler가 호출되는데, 해당 핸들러의 동작 과정에서 사용자 정보를 가지고 JwtTokenProvider를 통해 실제 사용될 access token을 발급(oauth 동작 과정에서의 access token과는 다름)하고 redirect됨. (이때 redirect 되는 uri가 바로 1번 과정에서 최초 요청 시 쿼리에 포함되어 요청된 redirect_uri임)
                        .failureHandler(oAuth2LoginFailureHandler) //인증이 정상적으로 이뤄지지 않은 경우에는 oAuth2AuthenticationFailureHandler가 호출
                )
                .logout(logout -> logout
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/")
                );
//                .oauth2Login((oauth2)->oauth2
//                        .successHandler(oAuth2LoginSuccessHandler)
//                        .failureHandler(oAuth2LoginFailureHandler)
//                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint.userService(customOAuth2UserService))
//                );

        // icon, css, js related
        // All materials in the basic page, css, image, and js subfolders are accessible, and can be accessed in h2-console.



        // 원래 스프링 시큐리티 필터 순서가 LogoutFilter 이후에 로그인 필터 동작
        // 따라서, LogoutFilter 이후에 우리가 만든 필터 동작하도록 설정
        // 순서 : LogoutFilter -> JwtAuthenticationProcessingFilter -> CustomJsonUsernamePasswordAuthenticationFilter
        http.addFilterAfter(customJsonUsernamePasswordAuthenticationFilter(), LogoutFilter.class);
        http.addFilterBefore(jwtAuthenticationProcessingFilter(), CustomJsonUsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * AuthenticationManager 설정 후 등록
     * PasswordEncoder를 사용하는 AuthenticationProvider 지정 (PasswordEncoder는 위에서 등록한 PasswordEncoder 사용)
     * FormLogin(기존 스프링 시큐리티 로그인)과 동일하게 DaoAuthenticationProvider 사용
     * UserDetailsService는 커스텀 LoginService로 등록
     * 또한, FormLogin과 동일하게 AuthenticationManager로는 구현체인 ProviderManager 사용(return ProviderManager)
     *
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
//        provider.setUserDetailsService(loginService);
        return new ProviderManager(provider);
    }

    /**
     * 로그인 성공 시 호출되는 LoginSuccessJWTProviderHandler 빈 등록
     */
//    @Bean
//    public LoginSuccessHandler loginSuccessHandler() {
//        return new LoginSuccessHandler(jwtService, userRepository);
//    }
//
//    /**
//     * 로그인 실패 시 호출되는 LoginFailureHandler 빈 등록
//     */
//    @Bean
//    public LoginFailureHandler loginFailureHandler() {
//        return new LoginFailureHandler();
//    }

    /**
     * CustomJsonUsernamePasswordAuthenticationFilter 빈 등록
     * 커스텀 필터를 사용하기 위해 만든 커스텀 필터를 Bean으로 등록
     * setAuthenticationManager(authenticationManager())로 위에서 등록한 AuthenticationManager(ProviderManager) 설정
     * 로그인 성공 시 호출할 handler, 실패 시 호출할 handler로 위에서 등록한 handler 설정
     */
    @Bean
    public CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordAuthenticationFilter() {
        CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordLoginFilter
                = new CustomJsonUsernamePasswordAuthenticationFilter(objectMapper);
        customJsonUsernamePasswordLoginFilter.setAuthenticationManager(authenticationManager());
//        customJsonUsernamePasswordLoginFilter.setAuthenticationSuccessHandler(loginSuccessHandler());
//        customJsonUsernamePasswordLoginFilter.setAuthenticationFailureHandler(loginFailureHandler());
        return customJsonUsernamePasswordLoginFilter;
    }


    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter() {
        JwtAuthenticationProcessingFilter jwtAuthenticationFilter = new JwtAuthenticationProcessingFilter(jwtService, userRepository);
        return jwtAuthenticationFilter;
    }


    //Swagger 설정

//    @Bean
//    public OpenAPI openAPI() {
//        String jwt = "JWT";
//        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt);
//        Components components = new Components().addSecuritySchemes(jwt, new SecurityScheme()
//                .name(jwt)
//                .type(SecurityScheme.Type.HTTP)
//                .scheme("bearer")
//                .bearerFormat("JWT")
//        );
//        return new OpenAPI()
//                .components(new Components())
//                .info(apiInfo())
//                .addSecurityItem(securityRequirement)
//                .components(components);
//    }
//    private Info apiInfo() {
//        return new Info()
//                .title("API Test") // API의 제목
//                .description("Let's practice Swagger UI") // API에 대한 설명
//                .version("1.0.0"); // API의 버전
//    }


}