package go.glogprototype.global.oauth2.userinfo;

import java.util.Map;

public class GoogleOAuth2UserInfo extends OAuth2UserInfo {

    public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getNickname() {
        return (String) attributes.get("name");
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("picture");
    }

    @Override
    public String getOAuth2Id() {
        return (String) attributes.get("sub"); // Google의 경우 'sub' 필드에 사용자 ID가 있습니다.
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getLocale() {
        return (String) attributes.get("locale");  // "locale" 키를 통해 로케일 정보를 가져옵니다.
    }
}
