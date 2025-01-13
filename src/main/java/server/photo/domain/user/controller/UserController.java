package server.photo.domain.user.controller;

import server.photo.domain.user.dto.request.UserLoginRequest;
import server.photo.domain.user.dto.request.UserSignUpRequest;
import server.photo.domain.user.dto.response.MyPageResponse;
import server.photo.domain.user.dto.response.UserLoginResponse;
import server.photo.domain.user.service.UserService;
import server.photo.global.handler.response.BaseResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public BaseResponse<Object> signup(@RequestBody UserSignUpRequest request) {
        return userService.signup(request);
    }

    @PostMapping("/login")
    public BaseResponse<UserLoginResponse> login(@RequestBody UserLoginRequest request, HttpServletResponse response){
        return userService.login(request, response);
    }

    @GetMapping("/users/{userId}")
    public BaseResponse<MyPageResponse> getMyPage(@PathVariable Long userId){
        return userService.getMyPage(userId);
    }
}
