package leets.enhance.domain.user.service;

import leets.enhance.domain.user.entity.User;
import leets.enhance.domain.user.exception.InvalidAccessException;
import leets.enhance.domain.user.exception.RegisterErrorException;
import leets.enhance.domain.user.model.request.UserLoginDto;
import leets.enhance.domain.user.model.request.UserRegisterDto;
import leets.enhance.domain.user.repository.UserRepository;
import leets.enhance.global.jwt.JwtToken;
import leets.enhance.global.jwt.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static leets.enhance.global.error.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;

    public User register(UserRegisterDto requestDto) {
        if(!requestDto.getIsChecked())
            throw new RegisterErrorException(UNCHECKED_EMAIL);

        if(!requestDto.getPwd().equals(requestDto.getCheckPwd()))
            throw new RegisterErrorException(MISMATCH_PASSWORD);

        User user = User.of()
                .email(requestDto.getEmail())
                .name(requestDto.getName())
                .pwd(passwordEncoder.encode(requestDto.getPwd()))
                .build();

        return userRepository.save(user);
    }

    public JwtToken login(UserLoginDto requestDto) {
        User user = userRepository.findUserByEmail(requestDto.getEmail())
                .orElseThrow(() -> new InvalidAccessException(INVALID_ID));

        if(!passwordEncoder.matches(requestDto.getPwd(), user.getPwd()))
            throw new InvalidAccessException(INVALID_PASSWORD);

        // 토큰 생성
        return jwtTokenService.generateToken(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPwd()));
    }

    public boolean check(String email) {
        return !userRepository.existsUserByEmail(email);
    }

    public User getUser(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new InvalidAccessException(INVALID_USER));
    }
}
