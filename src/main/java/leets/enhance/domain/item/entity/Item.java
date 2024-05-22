package leets.enhance.domain.item.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import static leets.enhance.domain.item.entity.Status.*;

@Entity
@Table(name = "item")
@Getter @Setter
@RequiredArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer level;
    private Boolean isBroken;

    @PrePersist
    public void init() {
        this.isBroken = false;
        this.level = 0;
    }

    @Builder(builderMethodName = "of")
    public Item(String name) {
        this.name = name;
    }

    public Status success() {
        this.level++;
        return SUCCESS;
    }

    public Status fail() {
        this.level--;
        return FAIL;
    }

    public Status destroy() {
        this.isBroken = true;
        return DESTROY;
    }
}
