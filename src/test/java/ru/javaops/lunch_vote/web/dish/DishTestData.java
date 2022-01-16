package ru.javaops.lunch_vote.web.dish;

import ru.javaops.lunch_vote.model.Dish;
import ru.javaops.lunch_vote.to.Menu;
import ru.javaops.lunch_vote.web.MatcherFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static ru.javaops.lunch_vote.web.restaurant.RestaurantTestData.restaurant1;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant", "date");
    public static final MatcherFactory.Matcher<Menu> MENU_MATCHER = MatcherFactory.usingEqualsComparator(Menu.class);

    public static final int DISH1_ID = 1;
    public static final int DISH2_ID = 2;
    public static final int DISH3_ID = 3;

    public static final Dish dish1 = new Dish(DISH1_ID, "Первое", new BigDecimal("35.35"));
    public static final Dish dish2 = new Dish(DISH2_ID, "Второе", new BigDecimal("70.00"));
    public static final Dish dish3 = new Dish(DISH3_ID, "Компот", new BigDecimal("35.35"));

    public static final List<Dish> dishes = List.of(dish1, dish2, dish3);

    public static final Menu menu1 = new Menu(restaurant1, dishes);

    public static Dish getNew() {
        return new Dish(null, "New dish", new BigDecimal("11.11"), LocalDate.now(), restaurant1);
    }

    public static Dish getUpdated() {
        return new Dish(dish1.id(), "Updated dish", new BigDecimal("22.22"));
    }
}