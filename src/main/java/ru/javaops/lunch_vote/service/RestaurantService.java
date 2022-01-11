package ru.javaops.lunch_vote.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.javaops.lunch_vote.model.Dish;
import ru.javaops.lunch_vote.model.Restaurant;
import ru.javaops.lunch_vote.repository.DishRepository;
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
    private final DishRepository dishRepository;

    @CacheEvict(value = "restaurants", allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        log.info("create {}", restaurant);
        checkNew(restaurant);
        return repository.save(restaurant);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
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

    @CacheEvict(value = "restaurants", allEntries = true)
    public void delete(int id) {
        log.info("delete {}", id);
        repository.deleteExisted(id);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public void enable(int id, boolean enabled) {
        log.info(enabled ? "enable {}" : "disable {}", id);
        Restaurant restaurant = checkOptional(get(id));
        restaurant.setAvailable(enabled);
    }

    public Optional<Restaurant> getWithDishes(int id) {
        log.info("getWithDishes {}", id);
        return repository.getWithDishes(id);
    }

    @Cacheable("restaurants")
    public List<Restaurant> getAllWithDishes() {
        log.info("getAllWithDishes");
        return repository.getAllWithDishes();
    }

    public Optional<Dish> getDish(int id, int dishId) {
        log.info("getDish restaurantId={} dishId={}", id, dishId);
        return getDishes(id).stream()
                .filter(d -> d.getId().equals(dishId))
                .findAny();
    }

    public List<Dish> getDishes(int id) {
        log.info("getDishes restaurantId={}", id);
        return checkOptional(getWithDishes(id)).getDishes();
    }

    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public Dish addDish(int id, Dish dish) {
        log.info("addDish restaurantId={} dish={}", id, dish);
        checkNew(dish);
        checkOptional(getWithDishes(id)).getDishes().add(dish);
        return dishRepository.save(dish);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public void updateDish(int id, Dish dish, int dishId) {
        log.info("updateDish restaurantId={} dish={} dishId={}", id, dish, dishId);
        checkDishInRestaurant(id, dishId);
        assureIdConsistent(dish, dishId);
        dishRepository.save(dish);
    }

    @Transactional
    @Modifying
    @CacheEvict(value = "restaurants", allEntries = true)
    public void deleteDish(int id, int dishId) {
        log.info("deleteDish restaurantId={} dishId={}", id, dishId);
        Dish dish = checkOptional(getDish(id, dishId));
        dishRepository.delete(dish);
    }

    private void checkDishInRestaurant(int id, int dishId) {
        Assert.notNull(getDish(id, dishId).orElse(null), "Dish not found");
    }
}
