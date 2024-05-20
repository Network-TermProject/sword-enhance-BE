package leets.enhance.domain.user.service;

import leets.enhance.domain.user.entity.User;
import leets.enhance.domain.user.model.request.UserRegisterDto;
import leets.enhance.domain.user.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static UserRepository userRepository;

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
}
