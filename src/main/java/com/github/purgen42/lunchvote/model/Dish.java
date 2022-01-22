package com.github.purgen42.lunchvote.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.purgen42.lunchvote.View;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

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

    @NotNull(groups = View.Persist.class)
    @JsonIgnore
    private LocalDate date;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull(groups = View.Persist.class)
    @JsonIgnore
    private Restaurant restaurant;

    public Dish(Integer id, String description, BigDecimal price, LocalDate date, Restaurant restaurant) {
        super(id);
        this.description = description;
        this.price = price;
        this.date = date;
        this.restaurant = restaurant;
    }

    public Dish(Integer id, String description, BigDecimal price) {
        super(id);
        this.description = description;
        this.price = price;
        this.date = null;
        this.restaurant = null;
    }
}
