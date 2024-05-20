package leets.enhance.domain.user.controller;

import leets.enhance.domain.user.entity.User;
import leets.enhance.domain.user.model.request.UserRegisterDto;
import leets.enhance.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}
