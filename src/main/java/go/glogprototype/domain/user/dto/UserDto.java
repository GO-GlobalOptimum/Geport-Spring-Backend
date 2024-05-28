package go.glogprototype.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDto {

    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    public static class UserSignUpDto {

        private String email;
        private String password;
        private String nickname;
        private int age;
        private String city;
    }
}
