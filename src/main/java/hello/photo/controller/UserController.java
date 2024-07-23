package hello.photo.controller;

import hello.photo.dto.user.request.UserLoginRequest;
import hello.photo.dto.user.request.UserSignupRequest;
import hello.photo.dto.user.response.MyPageResponse;
import hello.photo.dto.user.response.UserLoginResponse;
import hello.photo.dto.user.response.UserSignupResponse;
import hello.photo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name="User API", description = "User API(회원가입, 로그인)")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserSignupResponse> signup(@RequestBody UserSignupRequest request) {
        UserSignupResponse response = userService.signup(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest request){
        UserLoginResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{userId}")
    @Operation(summary = "마이페이지 조회")
    public ResponseEntity<MyPageResponse> getMyPage(@PathVariable Long userId){
        MyPageResponse response = userService.getMyPage(userId);
        return ResponseEntity.ok(response);
    }

}