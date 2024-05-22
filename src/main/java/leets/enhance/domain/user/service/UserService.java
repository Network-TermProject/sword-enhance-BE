package leets.enhance.domain.user.service;

import leets.enhance.domain.user.entity.User;
import leets.enhance.domain.user.model.request.UserCheckDuplicateIdDto;
import leets.enhance.domain.user.model.request.UserLoginDto;
import leets.enhance.domain.user.model.request.UserRegisterDto;
import leets.enhance.domain.user.repository.UserRepository;
import leets.enhance.global.jwt.JwtToken;
import leets.enhance.global.jwt.JwtTokenService;
import leets.enhance.global.jwt.RefreshToken;
import leets.enhance.global.jwt.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final TokenRepository tokenRepository;

    public User register(UserRegisterDto requestDto) {
        if(!requestDto.getIsChecked())
            throw new BadCredentialsException("이메일 확인을 먼저 해주세요.");

        if(!requestDto.getPwd().equals(requestDto.getCheckPwd()))
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");

        User user = User.of()
                .email(requestDto.getEmail())
                .name(requestDto.getName())
                .pwd(passwordEncoder.encode(requestDto.getPwd()))
                .build();

        return userRepository.save(user);
    }

    public JwtToken login(UserLoginDto requestDto) {
        User user = userRepository.findUserByEmail(requestDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 아이디입니다."));

        if(!passwordEncoder.matches(requestDto.getPwd(), user.getPwd()))
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");

        // 토큰 생성
        JwtToken token = jwtTokenService.generateToken(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPwd()));

        // refreshToken DB 저장
        tokenRepository.save(new RefreshToken(token.getRefreshToken()));
        return token;
    }

    public boolean check(UserCheckDuplicateIdDto requestDto) {
        return userRepository.existsUserByEmail(requestDto.getEmail());
    }

    public User getUser(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));
    }
}
