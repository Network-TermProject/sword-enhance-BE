package leets.enhance.domain.item.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter @Setter
@RequiredArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Level level;
    private Boolean isBroken;

    @Builder(builderMethodName = "of")
    public Item(String name) {
        this.name = name;
    }
}
