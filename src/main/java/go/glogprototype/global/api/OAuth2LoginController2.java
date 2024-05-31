package go.glogprototype.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Controller
public class OAuth2LoginController2 {

    private static final Logger log = LoggerFactory.getLogger(go.glogprototype.controller.OAuth2LoginController.class);
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
    public OAuth2LoginController2(ClientRegistrationRepository clientRegistrationRepository,
                                 OAuth2AuthorizedClientService authorizedClientService) {
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.authorizedClientService = authorizedClientService;
    }

    @GetMapping("/spring/login/oauth2/code/google")
    public void handleGoogleLogin(@RequestParam Map<String, String> params, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code = params.get(OAuth2ParameterNames.CODE);
        String state = params.get(OAuth2ParameterNames.STATE);
        log.info("----------체크3333-------------");
        System.out.println("----------체크1-------------");
        if (code != null && state != null) {
            ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId("google");

            OAuth2AuthorizationRequest authorizationRequest = OAuth2AuthorizationRequest.authorizationCode()
                    .clientId(clientRegistration.getClientId())
                    .authorizationUri(clientRegistration.getProviderDetails().getAuthorizationUri())
                    .redirectUri(clientRegistration.getRedirectUri())
                    .scopes(clientRegistration.getScopes())
                    .state(state)
                    .attributes(attributes -> {
                        attributes.put(OAuth2ParameterNames.REGISTRATION_ID, clientRegistration.getRegistrationId());
                    })
                    .build();

            OAuth2AuthorizationResponse authorizationResponse = OAuth2AuthorizationResponse.success(code)
                    .redirectUri(clientRegistration.getRedirectUri())
                    .state(state)
                    .build();

            OAuth2AuthorizedClient authorizedClient = this.authorizedClientService.loadAuthorizedClient(clientRegistration.getRegistrationId(), request.getUserPrincipal().getName());
            log.info("----------체크2-------------");
            // Custom logic to handle the authorized client
            String accessToken = authorizedClient.getAccessToken().getTokenValue();
            //response.sendRedirect("/"); // Redirect to the home page or desired URL
            response.sendRedirect("https://geport.blog");
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid OAuth2 request");
        }
    }
}
