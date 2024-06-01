package go.glogprototype.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDto {

     @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    @Builder
    public static class UserSignUpDto {

        private String email;
        private String password;
        private String nickname;
        private int age;
        private String city;
    }


    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    @Builder
    public static class UserInfoDto {

        private Long id;

        private String name;

        private String bio;

        private String imageUrl;


    }
}
