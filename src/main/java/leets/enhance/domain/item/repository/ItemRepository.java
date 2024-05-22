package leets.enhance.domain.item.repository;

import leets.enhance.domain.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findTop10ByIsBrokenOrderByLevelDesc(Boolean isBroken);
}
