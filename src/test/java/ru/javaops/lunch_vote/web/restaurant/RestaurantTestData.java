package ru.javaops.lunch_vote.web.restaurant;

import ru.javaops.lunch_vote.model.Dish;
import ru.javaops.lunch_vote.model.Restaurant;
import ru.javaops.lunch_vote.web.MatcherFactory;

import java.math.BigDecimal;
import java.util.List;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "dishes");
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_WITH_DISHES_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class);
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class);

    public static final int RESTAURANT1_ID = 1;
    public static final int RESTAURANT2_ID = 2;
    public static final int RESTAURANT3_ID = 3;
    public static final int NOT_FOUND = 100;

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT1_ID, "Restaurant 1", null, true);
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT2_ID, "Restaurant 2", null, true);
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT3_ID, "Restaurant 3", null, true);

    public static final Dish dish1 = new Dish(1, "Первое", new BigDecimal("35.35"));
    public static final Dish dish2 = new Dish(2, "Второе", new BigDecimal("70.00"));
    public static final Dish dish3 = new Dish(3, "Компот", new BigDecimal("35.35"));

    public static final List<Dish> dishes = List.of(dish1, dish2, dish3);

    public static Restaurant getNew() {
        return new Restaurant(null, "New restaurant", "Washington DC", true);
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT1_ID, "Updated restaurant", "somewhere", false);
    }

    public static Dish getNewDish() {
        return new Dish(null, "New dish", new BigDecimal("11.11"));
    }

    public static Dish getUpdatedDish() {
        return new Dish(dish1.id(), "Updated dish", new BigDecimal("22.22"));
    }
}
