package hello.photo.domain.user.service;

import hello.photo.domain.user.dto.CustomUserDetails;
import hello.photo.domain.user.entity.User;
import hello.photo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userData = userRepository.findByEmail(email);
        if(userData.isPresent()) {
            //UserDetails에 담아서 return하면 AuthenticationManager가 검증
            return new CustomUserDetails(userData.get());
        }
        return null;
    }
}