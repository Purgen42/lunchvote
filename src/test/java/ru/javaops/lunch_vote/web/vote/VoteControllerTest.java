package ru.javaops.lunch_vote.web.vote;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.lunch_vote.repository.VoteRepository;
import ru.javaops.lunch_vote.util.DateTimeUtil;
import ru.javaops.lunch_vote.util.validation.ValidationUtil;
import ru.javaops.lunch_vote.web.AbstractControllerTest;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.lunch_vote.web.restaurant.RestaurantTestData.RESTAURANT1_ID;
import static ru.javaops.lunch_vote.web.restaurant.RestaurantTestData.RESTAURANT2_ID;
import static ru.javaops.lunch_vote.web.user.UserTestData.USER_MAIL;

public class VoteControllerTest extends AbstractControllerTest {
    private static final String REST_URL = VoteController.REST_URL + '/';

    @Autowired
    private VoteRepository voteRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void vote() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL + RESTAURANT1_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Assertions.assertEquals(voteRepository.getAllByRestaurantIdAndDate(RESTAURANT1_ID, DateTimeUtil.today()).size(), 2);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void reVote() throws Exception {
        setMarginTime(LocalTime.MAX);
        perform(MockMvcRequestBuilders.post(REST_URL + RESTAURANT1_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        perform(MockMvcRequestBuilders.post(REST_URL + RESTAURANT2_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Assertions.assertEquals(voteRepository.getAllByRestaurantIdAndDate(RESTAURANT1_ID, DateTimeUtil.today()).size(), 1);
        Assertions.assertEquals(voteRepository.getAllByRestaurantIdAndDate(RESTAURANT2_ID, DateTimeUtil.today()).size(), 3);

        setMarginTime(LocalTime.of(11, 0));
    }


    @Test
    @WithUserDetails(value = USER_MAIL)
    void reVoteTooLate() throws Exception {
        setMarginTime(LocalTime.MIN);
        perform(MockMvcRequestBuilders.post(REST_URL + RESTAURANT1_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        perform(MockMvcRequestBuilders.post(REST_URL + RESTAURANT2_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());

        Assertions.assertEquals(voteRepository.getAllByRestaurantIdAndDate(RESTAURANT1_ID, DateTimeUtil.today()).size(), 2);
        Assertions.assertEquals(voteRepository.getAllByRestaurantIdAndDate(RESTAURANT2_ID, DateTimeUtil.today()).size(), 2);

        setMarginTime(LocalTime.of(11, 0));
    }

    private void setMarginTime(LocalTime time) throws NoSuchFieldException, IllegalAccessException {
        Field margin = ValidationUtil.class.getDeclaredField("marginTime");
        margin.setAccessible(true);
        margin.set(null, time);
    }
}
