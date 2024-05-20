package leets.enhance.domain.item.service;

import leets.enhance.domain.item.entity.Item;
import leets.enhance.domain.item.model.request.ItemRegisterDto;
import leets.enhance.domain.item.repository.ItemRepository;
import leets.enhance.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Item register(User user, ItemRegisterDto requestDto) {
        Item item = Item.of()
                .name(requestDto.getName())
                .build();

        return itemRepository.save(item);
    }

    public List<Item> findTop10() {
        return itemRepository.findTop10ByIsBrokenOrderByLevel(false);
    }
}
