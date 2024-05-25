package go.glogprototype.domain.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Authority {
    ADMIN("ROLE_ADMIN"),USER("ROLE_USER"),GUEST("ROLE_GUEST");

    private final String key;
}
