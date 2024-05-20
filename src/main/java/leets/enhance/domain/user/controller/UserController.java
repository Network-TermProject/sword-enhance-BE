package leets.enhance.domain.user.controller;

import leets.enhance.domain.user.entity.User;
import leets.enhance.domain.user.model.request.UserCheckDuplicateIdDto;
import leets.enhance.domain.user.model.request.UserLoginDto;
import leets.enhance.domain.user.model.request.UserRegisterDto;
import leets.enhance.domain.user.service.UserService;
import leets.enhance.global.jwt.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private static UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserRegisterDto requestDto) {
        User save = userService.register(requestDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(save.getId())
                .toUri();    // http://localhost:8080/users/{id}

        return ResponseEntity.created(location).build();
    }

    @PostMapping("/login")
    public ResponseEntity<JwtToken> login(@RequestBody UserLoginDto requestDto) {
        return ResponseEntity.ok(userService.login(requestDto));
    }

    @GetMapping("/check-duplicate-id")
    public ResponseEntity<Boolean> checkDuplicateId(@RequestBody UserCheckDuplicateIdDto requestDto) {
        return ResponseEntity.ok(userService.check(requestDto));
    }
}
