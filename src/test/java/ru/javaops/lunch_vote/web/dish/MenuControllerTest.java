package ru.javaops.lunch_vote.web.dish;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.lunch_vote.to.Menu;
import ru.javaops.lunch_vote.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.lunch_vote.web.dish.DishTestData.MENU_MATCHER;
import static ru.javaops.lunch_vote.web.dish.DishTestData.dishes;
import static ru.javaops.lunch_vote.web.dish.DishTestData.menu1;
import static ru.javaops.lunch_vote.web.restaurant.RestaurantTestData.*;
import static ru.javaops.lunch_vote.web.user.UserTestData.USER_MAIL;

public class MenuControllerTest extends AbstractControllerTest {
    private static final String REST_URL = MenuController.REST_URL + '/';

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(menu1));
    }
}
