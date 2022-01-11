package ru.javaops.lunch_vote.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "dishes")
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dish extends BaseEntity {
    @NotBlank
    private String description;

    @NotNull
    private BigDecimal price;

    public Dish(Integer id, String description, BigDecimal price) {
        super(id);
        this.description = description;
        this.price = price;
    }
}
