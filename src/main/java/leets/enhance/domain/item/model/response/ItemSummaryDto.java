package leets.enhance.domain.item.model.response;

import leets.enhance.domain.item.entity.Item;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ItemSummaryDto {
    private String swordName;
    private String userName;
    private Integer level;

    public ItemSummaryDto(Item item) {
        this.swordName = item.getName();
        this.userName = item.getUser().getName();
        this.level = item.getLevel();
    }
}
