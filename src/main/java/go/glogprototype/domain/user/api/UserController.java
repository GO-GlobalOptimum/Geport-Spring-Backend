package go.glogprototype.domain.user.api;

import go.glogprototype.domain.user.dao.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class UserController {

   private final MemberRepository memberRepository;

   @GetMapping("/users")
    public String userslist() {
       
       return "userList";
   }

}
