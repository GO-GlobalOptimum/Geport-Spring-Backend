package go.glogprototype.domain.user.api;

import go.glogprototype.domain.user.application.UserService;
import go.glogprototype.domain.user.dao.UserRepository;
import go.glogprototype.domain.user.dto.UserDto;
import go.glogprototype.domain.user.dto.UserDto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.Cookie;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/spring/user")
public class UserController {

   private final UserRepository memberRepository;
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

        //유저 정보 가져오기
    @GetMapping("/myInfo")
    public UserInfoDto myInfo(@CookieValue(value = "memberId") Cookie cookie) {

        Long memberId = Long.valueOf(cookie.getValue());

        return userService.showUserInfo(memberId);
    }

    //유저 정보 수정하기
    @PostMapping("/myInfo")
    public UserInfoDto EditMyInfo(@CookieValue(value = "memberId") Cookie cookie,
                                  @RequestBody UserInfoDto userInfoDto) {

        Long memberId = Long.valueOf(cookie.getValue());


        return userService.updateUserInfo(memberId, userInfoDto);

    }

   


}
