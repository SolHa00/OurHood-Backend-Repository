package hello.photo.domain.user.controller;

import hello.photo.domain.user.dto.request.UserLoginRequest;
import hello.photo.domain.user.dto.request.UserSignupRequest;
import hello.photo.domain.user.dto.response.MyPageResponse;
import hello.photo.domain.user.service.UserService;
import hello.photo.global.response.ApiResponse;
import hello.photo.global.response.DataResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name="User API", description = "User API(회원가입, 로그인, 마이페이지)")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ApiResponse signup(@RequestBody UserSignupRequest request) {
        return userService.signup(request);
    }

    @PostMapping("/login")
    public ApiResponse login(@RequestBody UserLoginRequest request, HttpServletResponse response){
        return userService.login(request, response);
    }

    @GetMapping("/users/{userId}")
    public DataResponseDto<MyPageResponse> getMyPage(@PathVariable Long userId){
        return userService.getMyPage(userId);
    }
}
