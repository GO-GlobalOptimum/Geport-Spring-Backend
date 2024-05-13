package go.glogprototype.domain.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@RequiredArgsConstructor
public class MessageToAdmin {


    @Id @GeneratedValue
    @Column(name = "message_to_admin_id")
    private Long id;

    private String title;

    private String content;

    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Member admin;


}
