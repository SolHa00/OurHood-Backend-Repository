package hello.photo.domain.user.controller;

import hello.photo.domain.user.dto.request.UserLoginRequest;
import hello.photo.domain.user.dto.request.UserSignupRequest;
import hello.photo.domain.user.dto.response.MyPageResponse;
import hello.photo.domain.user.dto.response.UserSignupResponse;
import hello.photo.domain.user.dto.response.UserLoginResponse;
import hello.photo.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
@Tag(name="User API", description = "User API(회원가입, 로그인)")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @PostMapping("/signup")
    public UserSignupResponse signup(@RequestBody UserSignupRequest request) {
        log.info("Signup request 들어옴");
        return userService.signup(request);
    }

    @PostMapping("/login")
    public UserLoginResponse login(@RequestBody UserLoginRequest request){
        return userService.login(request);
    }

    @GetMapping("/users/{userId}")
    @Operation(summary = "마이페이지 조회")
    public MyPageResponse getMyPage(@PathVariable Long userId){
        return userService.getMyPage(userId);
    }

}