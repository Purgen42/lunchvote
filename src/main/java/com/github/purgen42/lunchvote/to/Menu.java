package com.github.purgen42.lunchvote.to;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import com.github.purgen42.lunchvote.model.Dish;
import com.github.purgen42.lunchvote.model.Restaurant;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Menu extends BaseTo {
    private Restaurant restaurant;

    private List<Dish> dishes;

    public Menu(Restaurant restaurant, List<Dish> dishes) {
        super(restaurant.getId());
        this.restaurant = restaurant;
        this.dishes = dishes;
    }
}
