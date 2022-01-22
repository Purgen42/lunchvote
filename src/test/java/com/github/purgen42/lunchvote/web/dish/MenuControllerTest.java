package com.github.purgen42.lunchvote.web.dish;

import com.github.purgen42.lunchvote.web.AbstractControllerTest;
import com.github.purgen42.lunchvote.web.restaurant.RestaurantTestData;
import com.github.purgen42.lunchvote.web.user.UserTestData;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.github.purgen42.lunchvote.web.dish.DishTestData.MENU_MATCHER;
import static com.github.purgen42.lunchvote.web.dish.DishTestData.menu1;

public class MenuControllerTest extends AbstractControllerTest {
    private static final String REST_URL = MenuController.REST_URL + '/';

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void get() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.get(REST_URL + RestaurantTestData.RESTAURANT1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(menu1));
    }
}
