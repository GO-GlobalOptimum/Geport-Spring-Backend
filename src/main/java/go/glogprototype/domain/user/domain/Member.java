package go.glogprototype.domain.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@RequiredArgsConstructor
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")

    private String g_mail;

    private Long id;

    private String name;

    private String loginId;

    private String loginPw;

    private String nickName;

    @Enumerated
    private Authority authority;

    private String email;

    private LocalDateTime birthDate;

}
