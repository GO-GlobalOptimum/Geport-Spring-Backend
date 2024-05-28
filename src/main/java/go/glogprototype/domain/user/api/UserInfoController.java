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

    @GetMapping("/{id}/edit")
    public UserEditDto getMember(@PathVariable Long id) {
        return userService.getMemberEditDTO(id);
    }

    @PutMapping("/{id}/edit")
    public void editMember(@PathVariable Long id, @RequestBody UserEditDto memberEditDTO) {
        userService.editMember(id, memberEditDTO);
    }
}