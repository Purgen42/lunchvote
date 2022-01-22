package com.github.purgen42.lunchvote.web.restaurant;

import com.github.purgen42.lunchvote.web.MatcherFactory;
import com.github.purgen42.lunchvote.model.Restaurant;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "dishes");
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_WITH_DISHES_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class);

    public static final int RESTAURANT1_ID = 1;
    public static final int RESTAURANT2_ID = 2;
    public static final int RESTAURANT3_ID = 3;
    public static final int NOT_FOUND = 100;

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT1_ID, "Restaurant 1", null, true);
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT2_ID, "Restaurant 2", null, true);
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT3_ID, "Restaurant 3", null, true);

    public static Restaurant getNew() {
        return new Restaurant(null, "New restaurant", "Washington DC", true);
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT1_ID, "Updated restaurant", "somewhere", false);
    }
}
