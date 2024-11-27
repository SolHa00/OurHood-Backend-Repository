package hello.photo.domain.user.service;

import hello.photo.domain.user.dto.CustomUserDetails;
import hello.photo.domain.user.entity.User;
import hello.photo.domain.user.repository.UserRepository;
import hello.photo.global.exception.LogInFailException;
import hello.photo.global.response.Code;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new LogInFailException(Code.LOGIN_FAIL, Code.LOGIN_FAIL.getMessage());
        }
        return new CustomUserDetails(user);
    }
}
