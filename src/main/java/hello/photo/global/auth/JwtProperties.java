package hello.photo.global.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties("jwt") // yml 설정 값을 가져와서 사용
public class JwtProperties {
    private String issuer;
    private String secretKey;
}