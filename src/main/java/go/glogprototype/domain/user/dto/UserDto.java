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
    public static class UserInfoDto {

//        private Long id;

        private String nickName;

        private String bio;

        private String imageUrl;

        private String mbti;

        private int age;

        private String gender;

        private String phoneNumber;

        @Builder

        public UserInfoDto(String nickName, String bio, String imageUrl, String mbti, int age, String gender, String phoneNumber) {
            this.nickName = nickName;
            this.bio = bio;
            this.imageUrl = imageUrl;
            this.mbti = mbti;
            this.age = age;
            this.gender = gender;
            this.phoneNumber = phoneNumber;
        }
    }
}
