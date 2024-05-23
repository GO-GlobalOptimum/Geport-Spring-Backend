package go.glogprototype.domain.user.api;

import go.glogprototype.domain.user.application.UserService;
import go.glogprototype.domain.user.dao.MemberRepository;
import go.glogprototype.domain.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class UserController {

   private final MemberRepository memberRepository;
   private final UserService userService;

   @GetMapping("/users")
    public String userslist() {
       
       return "userList";
   }

    @PostMapping("/sign-up")
    public String signUp(@RequestBody UserDto.UserSignUpDto userSignUpDto) throws Exception {
        userService.signUp(userSignUpDto);
        return "회원가입 성공";
    }

    @GetMapping("/jwt-test")
    public String jwtTest() {
        return "jwtTest 요청 성공";
    }

    @GetMapping("/sign-up")
    public String oauth2SingUp() {
        return "oauth2Sign";
    }

}
