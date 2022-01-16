package ru.javaops.lunch_vote.to;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.javaops.lunch_vote.model.Dish;
import ru.javaops.lunch_vote.model.Restaurant;

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
