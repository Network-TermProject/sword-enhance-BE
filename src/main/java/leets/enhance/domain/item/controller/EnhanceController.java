package leets.enhance.domain.item.controller;

import leets.enhance.domain.item.entity.Item;
import leets.enhance.domain.item.entity.Status;
import leets.enhance.domain.item.model.request.ItemEnhanceDto;
import leets.enhance.domain.item.model.response.ItemEnhanceResponseDto;
import leets.enhance.domain.item.service.ItemService;
import leets.enhance.global.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enhance")
@RequiredArgsConstructor
public class EnhanceController {

    private final ItemService itemService;

    @PostMapping()
    public BaseResponse<ItemEnhanceResponseDto> enhance(@RequestBody ItemEnhanceDto requestDto) {
        Item item = itemService.getItem(requestDto.getId());
        Status status = itemService.enhance(item, requestDto.getIsBoosted());

        return BaseResponse.of(
                ItemEnhanceResponseDto.builder()
                .item(item)
                .status(status)
                .build());
    }
}
