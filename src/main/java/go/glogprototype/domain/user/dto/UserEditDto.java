package go.glogprototype.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEditDto {
    private String nickName;
    private String bio;
    private String profileImage;
    private String mbti;
    private int age;
    private String gender;
    private String phoneNumber;
    private String email;


}