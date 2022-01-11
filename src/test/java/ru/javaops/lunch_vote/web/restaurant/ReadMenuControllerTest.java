package ru.javaops.lunch_vote.web.restaurant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.lunch_vote.model.Restaurant;
import ru.javaops.lunch_vote.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.lunch_vote.web.restaurant.RestaurantTestData.*;
import static ru.javaops.lunch_vote.web.user.UserTestData.USER_MAIL;

public class ReadMenuControllerTest extends AbstractControllerTest {
    private static final String REST_URL = ReadMenuController.REST_URL + '/';

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(restaurant1));

        Restaurant restaurant = RESTAURANT_WITH_DISHES_MATCHER.readFromJson(action);
        Assertions.assertEquals(restaurant.getDishes(), dishes);
    }
}
