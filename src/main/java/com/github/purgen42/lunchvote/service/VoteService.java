package com.github.purgen42.lunchvote.service;

import com.github.purgen42.lunchvote.model.Vote;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.purgen42.lunchvote.model.Restaurant;
import com.github.purgen42.lunchvote.repository.VoteRepository;
import com.github.purgen42.lunchvote.to.VoteStat;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.github.purgen42.lunchvote.util.DateTimeUtil.now;
import static com.github.purgen42.lunchvote.util.DateTimeUtil.today;
import static com.github.purgen42.lunchvote.util.validation.ValidationUtil.checkOptional;
import static com.github.purgen42.lunchvote.util.validation.ValidationUtil.checkRevotingAvailable;

@Service
@Slf4j
@AllArgsConstructor
@CacheConfig(cacheNames = "votes")
public class VoteService {
    private final VoteRepository repository;
    private final UserService userService;
    private final RestaurantService restaurantService;

    @Transactional
    @CacheEvict(value = "votes", allEntries = true)
    public Vote vote(int restaurantId, int userId) {
        Vote vote = new Vote();
        Optional<Vote> voteOptional = repository.findByUserIdAndDate(userId, today());
        if (voteOptional.isPresent()) {
            checkRevotingAvailable(now());
            vote.setId(voteOptional.get().getId());
        }
        vote.setRestaurant(checkOptional(restaurantService.get(restaurantId)));
        vote.setUser(userService.safeGet(userId));
        vote.setDate(today());
        return repository.save(vote);
    }

    @Cacheable("votes")
    @Transactional
    public List<VoteStat> getVoteStat() {
        Map<Restaurant, Long> votesMap = repository.getAllByDate(today()).stream()
                .collect(Collectors.groupingBy(Vote::getRestaurant, Collectors.counting()));
        restaurantService.getAvailable().forEach(r -> votesMap.putIfAbsent(r, 0L));
        return votesMap.entrySet().stream()
                .map(e -> new VoteStat(e.getKey(), e.getValue()))
                .sorted()
                .collect(Collectors.toList());
    }
}
