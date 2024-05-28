package go.glogprototype.domain.user.api;

import go.glogprototype.domain.user.application.UserService;
import go.glogprototype.domain.user.dto.UserEditDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spring/userInfo")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserService userService;

    @GetMapping("/{email}/edit")
    public UserEditDto getMember(@PathVariable String email) {
        return userService.getMemberEditDTO(email);
    }

    @PutMapping("/{email}/edit")
    public void editMember(@PathVariable String email, @RequestBody UserEditDto memberEditDTO) {
        userService.editMember(email, memberEditDTO);
    }
}