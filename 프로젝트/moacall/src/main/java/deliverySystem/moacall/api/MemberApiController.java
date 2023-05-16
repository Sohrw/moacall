package deliverySystem.moacall.api;

import deliverySystem.moacall.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/login")
    public int login(@RequestParam(value = "user_name") String user_name, @RequestParam(value = "pw") String pw) {
        Boolean isUser = memberService.memberPermissionForLogin(user_name, pw);
        if(isUser == true) {
            return 200;
        } else {
            return 444;
        }
    }


}
