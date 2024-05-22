package leets.enhance.domain.item.service;

import jakarta.transaction.Transactional;
import leets.enhance.domain.item.entity.DestroyProbability;
import leets.enhance.domain.item.entity.Item;
import leets.enhance.domain.item.entity.Status;
import leets.enhance.domain.item.entity.SuccessProbability;
import leets.enhance.domain.item.model.request.ItemRegisterDto;
import leets.enhance.domain.item.repository.ItemRepository;
import leets.enhance.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public Item register(User user, ItemRegisterDto requestDto) {
        Item item = Item.of()
                .name(requestDto.getName())
                .build();

        user.addItem(item);

        return itemRepository.save(item);
    }

    public List<Item> findTop10() {
        return itemRepository.findTop10ByIsBrokenOrderByLevelDesc(false);
    }

    @Transactional
    public Status enhance(Item item) {
        if(item.getIsBroken()) {
            throw new RuntimeException("이미 파괴된 검입니다."); // 수정: 커스텀 예외로 처리
        }

        if(Math.random() <= SuccessProbability.getProbability(item.getLevel())) {   // 강화 성공
            return item.success();
        } else {    // 실패 시
            if(Math.random() <= DestroyProbability.getProbability(item.getLevel())) // 파괴 성공
                return item.destroy();
            return item.fail(); // 파괴 실패
        }
    }

    public Item getItem(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 검입니다."));
    }
}
