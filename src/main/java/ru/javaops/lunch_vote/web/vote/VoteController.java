package ru.javaops.lunch_vote.web.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javaops.lunch_vote.model.Vote;
import ru.javaops.lunch_vote.service.VoteService;
import ru.javaops.lunch_vote.to.VoteStat;
import ru.javaops.lunch_vote.web.SecurityUtil;

import java.util.List;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {
    static final String REST_URL = "/api/vote";

    @Autowired
    private VoteService service;

    @PostMapping(value = "/{restaurantId}")
    public Vote vote(@PathVariable int restaurantId) {
        return service.vote(restaurantId, SecurityUtil.authId());
    }

    @GetMapping("/stat")
    public List<VoteStat> stat() {
        return service.getVoteStat();
    }
}
