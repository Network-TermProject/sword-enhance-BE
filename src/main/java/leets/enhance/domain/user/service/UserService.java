package leets.enhance.domain.user.service;

import leets.enhance.domain.user.entity.User;
import leets.enhance.domain.user.model.request.UserCheckDuplicateIdDto;
import leets.enhance.domain.user.model.request.UserLoginDto;
import leets.enhance.domain.user.model.request.UserRegisterDto;
import leets.enhance.domain.user.repository.UserRepository;
import leets.enhance.global.jwt.JwtToken;
import leets.enhance.global.jwt.JwtTokenService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static UserRepository userRepository;
    private static PasswordEncoder passwordEncoder;
    private static JwtTokenService jwtTokenService;

    public User register(UserRegisterDto requestDto) {
        if(!requestDto.getPwd().equals(requestDto.getCheckPwd()))
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");

        User user = User.of()
                .email(requestDto.getEmail())
                .name(requestDto.getName())
                .pwd(requestDto.getPwd())
                .build();

        return userRepository.save(user);
    }

    public JwtToken login(UserLoginDto requestDto) {
        User user = userRepository.findUserByEmail(requestDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 아이디입니다."));

        if(!passwordEncoder.matches(requestDto.getPwd(), user.getPwd()))
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");

        // 토큰 생성
        return jwtTokenService.generateToken(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPwd()));
    }

    public boolean check(UserCheckDuplicateIdDto requestDto) {
        return userRepository.existsUserByEmail(requestDto.getEmail());
    }
}
