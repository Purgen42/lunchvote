package ru.javaops.lunch_vote.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.javaops.lunch_vote.model.Dish;
import ru.javaops.lunch_vote.repository.DishRepository;
import ru.javaops.lunch_vote.repository.RestaurantRepository;

import java.util.List;
import java.util.Optional;

import static ru.javaops.lunch_vote.util.validation.ValidationUtil.*;

@Service
@Slf4j
@AllArgsConstructor
@CacheConfig(cacheNames = "dishes")
public class DishService {
    private final DishRepository repository;
    private final RestaurantRepository restaurantRepository;

    public Optional<Dish> get(int id, int restaurantId) {
        log.info("getDish restaurantId={} dishId={}", restaurantId, id);
        return getAll(restaurantId).stream()
                .filter(d -> d.getId().equals(id))
                .findAny();
    }

    @Cacheable("dishes")
    public List<Dish> getAll(int restaurantId) {
        log.info("getDishes restaurantId={}", restaurantId);
        return checkOptional(restaurantRepository.getWithDishes(restaurantId)).getDishes();
    }

    @Transactional
    @CacheEvict(value = "dishes", allEntries = true)
    public Dish add(Dish dish, int restaurantId) {
        log.info("addDish restaurantId={} dish={}", restaurantId, dish);
        checkNew(dish);
        checkOptional(restaurantRepository.getWithDishes(restaurantId)).getDishes().add(dish);
        return repository.save(dish);
    }

    @CacheEvict(value = "dishes", allEntries = true)
    public void update(Dish dish, int id, int restaurantId) {
        log.info("updateDish restaurantId={} dish={} dishId={}", restaurantId, dish, id);
        checkDishInRestaurant(id, restaurantId);
        assureIdConsistent(dish, id);
        repository.save(dish);
    }

    @Transactional
    @CacheEvict(value = "dishes", allEntries = true)
    public void delete(int id, int restaurantId) {
        log.info("deleteDish restaurantId={} dishId={}", restaurantId, id);
        Dish dish = checkOptional(get(id, restaurantId));
        repository.delete(dish);
    }

    private void checkDishInRestaurant(int id, int restaurantId) {
        Assert.notNull(get(id, restaurantId).orElse(null), "Dish not found");
    }
}

