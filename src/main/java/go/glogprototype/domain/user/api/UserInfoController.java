package go.glogprototype.domain.user.api;

import go.glogprototype.domain.user.application.UserService;
import go.glogprototype.domain.user.dto.UserEditDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spring/userInfo")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserService userService;

    @GetMapping("/profile")
    public UserEditDto getMember(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.getMemberEditDTO(userDetails.getUsername());
    }

    @PutMapping("/profile")
    public void editMember(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UserEditDto memberEditDTO) {
        userService.editMember(userDetails.getUsername(), memberEditDTO);
    }
}