package hello.photo.config.jwt;

import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    public String createToken(String email) {
        return "token";
    }
}

