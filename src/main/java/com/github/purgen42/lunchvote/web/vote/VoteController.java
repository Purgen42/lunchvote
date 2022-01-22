package com.github.purgen42.lunchvote.web.vote;

import com.github.purgen42.lunchvote.model.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.github.purgen42.lunchvote.service.VoteService;
import com.github.purgen42.lunchvote.to.VoteStat;
import com.github.purgen42.lunchvote.web.SecurityUtil;

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
