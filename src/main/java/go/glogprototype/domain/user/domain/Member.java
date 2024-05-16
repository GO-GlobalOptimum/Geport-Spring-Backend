package go.glogprototype.domain.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Member {
    @Id @GeneratedValue
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

    @Enumerated
    private Authority authority;

    private String profileImage;

    private String email;

    private LocalDateTime birthDate;

    private boolean isDeleted;

    private Long geportId;

    private LocalDateTime deletedDate;
}
