package go.glogprototype.domain.user.api;

import go.glogprototype.domain.user.application.UserService;
import go.glogprototype.domain.user.dao.UserRepository;
import go.glogprototype.domain.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("spring/user")
public class UserController {

   private final UserRepository userRepository;
   private final UserService userService;

    @PostMapping("/sign-up")
    public String signUp(@RequestBody UserDto.UserSignUpDto userSignUpDto) throws Exception {
        userService.signUp(userSignUpDto);
        return "회원가입 성공";
    }

    @GetMapping("/jwt-test")
    public String jwtTest() {
        return "jwtTest 요청 성공";
    }

    @GetMapping("/users")
    public List<UserDto.UserSignUpDto> userList() {
        return userService.findAll();
    }


}
