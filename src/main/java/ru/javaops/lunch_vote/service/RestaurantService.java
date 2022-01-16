package ru.javaops.lunch_vote.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.lunch_vote.model.Restaurant;
import ru.javaops.lunch_vote.repository.RestaurantRepository;

import java.util.List;
import java.util.Optional;

import static ru.javaops.lunch_vote.util.validation.ValidationUtil.*;

@Service
@Slf4j
@AllArgsConstructor
@CacheConfig(cacheNames = "restaurants")
public class RestaurantService {
    private final RestaurantRepository repository;

    @CacheEvict(value = {"restaurants", "dishes", "votes"}, allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        log.info("create {}", restaurant);
        checkNew(restaurant);
        return repository.save(restaurant);
    }

    @CacheEvict(value = {"restaurants", "dishes", "votes"}, allEntries = true)
    public void update(Restaurant restaurant, int id) {
        log.info("update {} with id={}", restaurant, id);
        assureIdConsistent(restaurant, id);
        repository.save(restaurant);
    }

    public Optional<Restaurant> get(int id) {
        log.info("get {}", id);
        return repository.findById(id);
    }

    public List<Restaurant> getAll() {
        log.info("getAll");
        return repository.findAll();
    }

    public List<Restaurant> getAvailable() {
        log.info("getAvailable");
        return repository.getAllByAvailableTrue();
    }

    @CacheEvict(value = {"restaurants", "dishes", "votes"}, allEntries = true)
    public void delete(int id) {
        log.info("delete {}", id);
        repository.deleteExisted(id);
    }

    @CacheEvict(value = {"restaurants", "dishes", "votes"}, allEntries = true)
    @Transactional
    public void enable(int id, boolean enabled) {
        log.info(enabled ? "enable {}" : "disable {}", id);
        Restaurant restaurant = checkOptional(get(id));
        restaurant.setAvailable(enabled);
    }
}
