package com.github.purgen42.lunchvote.web.vote;

import com.github.purgen42.lunchvote.repository.VoteRepository;
import com.github.purgen42.lunchvote.util.DateTimeUtil;
import com.github.purgen42.lunchvote.util.validation.ValidationUtil;
import com.github.purgen42.lunchvote.web.AbstractControllerTest;
import com.github.purgen42.lunchvote.web.restaurant.RestaurantTestData;
import com.github.purgen42.lunchvote.web.user.UserTestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.lang.reflect.Field;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VoteControllerTest extends AbstractControllerTest {
    private static final String REST_URL = VoteController.REST_URL + '/';

    @Autowired
    private VoteRepository voteRepository;

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void vote() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL + RestaurantTestData.RESTAURANT1_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Assertions.assertEquals(voteRepository.getAllByRestaurantIdAndDate(RestaurantTestData.RESTAURANT1_ID, DateTimeUtil.today()).size(), 2);
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void reVote() throws Exception {
        setMarginTime(LocalTime.MAX);
        perform(MockMvcRequestBuilders.post(REST_URL + RestaurantTestData.RESTAURANT1_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        perform(MockMvcRequestBuilders.post(REST_URL + RestaurantTestData.RESTAURANT2_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Assertions.assertEquals(voteRepository.getAllByRestaurantIdAndDate(RestaurantTestData.RESTAURANT1_ID, DateTimeUtil.today()).size(), 1);
        Assertions.assertEquals(voteRepository.getAllByRestaurantIdAndDate(RestaurantTestData.RESTAURANT2_ID, DateTimeUtil.today()).size(), 3);

        setMarginTime(LocalTime.of(11, 0));
    }


    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void reVoteTooLate() throws Exception {
        setMarginTime(LocalTime.MIN);
        perform(MockMvcRequestBuilders.post(REST_URL + RestaurantTestData.RESTAURANT1_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        perform(MockMvcRequestBuilders.post(REST_URL + RestaurantTestData.RESTAURANT2_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());

        Assertions.assertEquals(voteRepository.getAllByRestaurantIdAndDate(RestaurantTestData.RESTAURANT1_ID, DateTimeUtil.today()).size(), 2);
        Assertions.assertEquals(voteRepository.getAllByRestaurantIdAndDate(RestaurantTestData.RESTAURANT2_ID, DateTimeUtil.today()).size(), 2);

        setMarginTime(LocalTime.of(11, 0));
    }

    private void setMarginTime(LocalTime time) throws NoSuchFieldException, IllegalAccessException {
        Field margin = ValidationUtil.class.getDeclaredField("marginTime");
        margin.setAccessible(true);
        margin.set(null, time);
    }
}
