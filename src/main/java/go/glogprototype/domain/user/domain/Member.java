package go.glogprototype.domain.user.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String g_mail;

    private String bio;

    private int age;

    private String gender;

    private String phoneNumber;

    private int followerCount;

    private int followingCount;

    private String name;

    private String nickName;

    private String mbti;

    @Enumerated(value = EnumType.STRING)
    private Authority authority;

    private String email;

    // private LocalDateTime birthDate;

    private boolean isDeleted;

    private Long geportId;

    private LocalDateTime deletedDate;

    private String refreshToken; // 리프레시 토큰

    private String socialId;

    private String password;

    private String city;

    private String imageUrl;

    private String location;



    // 유저 권한 설정 메소드
    public void authorizeUser() {
        this.authority = Authority.USER;
    }

    // 비밀번호 암호화 메소드
    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    @Builder
    public Member(String email, String name, String socialId, Authority authority, String imageUrl, String location) {
        this.email = email;
        this.name = name;
        this.socialId = socialId;
        this.imageUrl = imageUrl;
        this.location = location;
        this.authority = authority != null ? authority : Authority.USER; // null 체크
    }
    //== 유저 필드 업데이트 ==//
    public void updateNickname(String updateNickname) {
        this.nickName = updateNickname;
    }

    public void updateAge(int updateAge) {
        this.age = updateAge;
    }

    public void updateCity(String updateCity) {
        this.city = updateCity;
    }

    public void updatePassword(String updatePassword, PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(updatePassword);
    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }
    public void update(UserInfoDto userInfoDto) {
        this.name = userInfoDto.getName();
        this.bio = userInfoDto.getBio();
        this.imageUrl = userInfoDto.getImageUrl();
    }


}
