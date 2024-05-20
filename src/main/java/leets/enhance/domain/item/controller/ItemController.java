package leets.enhance.domain.item.controller;

import leets.enhance.domain.item.entity.Item;
import leets.enhance.domain.item.model.request.ItemRegisterDto;
import leets.enhance.domain.item.service.ItemService;
import leets.enhance.domain.user.entity.User;
import leets.enhance.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final UserService userService;

    @PostMapping()
    public ResponseEntity<Item> register(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ItemRegisterDto requestDto) {
        User user = userService.getUser(userDetails.getUsername());
        return ResponseEntity.ok(itemService.register(user, requestDto));
    }

    @GetMapping("")
    public ResponseEntity<List<Item>> findMyItems(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUser(userDetails.getUsername());
        return ResponseEntity.ok(user.getItems());
    }

    @GetMapping("/top10")
    public ResponseEntity<List<Item>> findTop10() {
        return ResponseEntity.ok(itemService.findTop10());
    }
}
