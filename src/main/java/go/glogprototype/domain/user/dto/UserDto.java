package go.glogprototype.domain.user.dto;

import lombok.*;

@Builder
@Data
public class UserDto {

    @NoArgsConstructor
    @Data
    @AllArgsConstructor
    public static class UserSignUpDto {

        private String email;
        private String password;
        private String nickname;
        private int age;
        private String city;
    }


    @NoArgsConstructor
    @Data
    @AllArgsConstructor
    public static class UserInfoDto {

//        private Long id;

        private String name;

        private String bio;

        private String imageUrl;


    }
}
