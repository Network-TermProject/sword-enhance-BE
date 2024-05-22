package leets.enhance.domain.item.controller;

import leets.enhance.domain.item.entity.Item;
import leets.enhance.domain.item.model.response.ItemEnhanceResponseDto;
import leets.enhance.domain.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enhance")
@RequiredArgsConstructor
public class enhanceController {

    private final ItemService itemService;

    @PostMapping()
    public ResponseEntity<ItemEnhanceResponseDto> enhance(@RequestParam Long itemId) {
        Item item = itemService.getItem(itemId);
        return ResponseEntity.ok(
                ItemEnhanceResponseDto.builder()
                .item(item)
                .status(itemService.enhance(item))
                .build());
    }
}
