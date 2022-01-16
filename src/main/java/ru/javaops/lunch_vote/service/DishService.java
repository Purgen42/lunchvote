package ru.javaops.lunch_vote.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.lunch_vote.model.Dish;
import ru.javaops.lunch_vote.model.Restaurant;
import ru.javaops.lunch_vote.repository.DishRepository;
import ru.javaops.lunch_vote.repository.RestaurantRepository;
import ru.javaops.lunch_vote.to.Menu;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.javaops.lunch_vote.util.DateTimeUtil.today;
import static ru.javaops.lunch_vote.util.validation.ValidationUtil.*;

@Service
@Slf4j
@AllArgsConstructor
@CacheConfig(cacheNames = "dishes")
public class DishService {
    private final DishRepository repository;
    private final RestaurantRepository restaurantRepository;

    public Optional<Dish> get(int id, int restaurantId) {
        log.info("get id={} restaurantId={}", id, restaurantId);
        return repository.findByIdAndDateAndRestaurantId(id, today(), restaurantId);
    }

    public List<Dish> getAll(int restaurantId) {
        log.info("getAll restaurantId={}", restaurantId);
        return repository.findAllByDateAndRestaurantId(today(), restaurantId);
    }

    @CacheEvict(value = "dishes", allEntries = true)
    public Dish create(Dish dish, int restaurantId) {
        log.info("add  dish={} restaurantId={}\", id, restaurantId", dish, restaurantId);
        checkNew(dish);
        dish.setDate(today());
        dish.setRestaurant(checkOptional(restaurantRepository.findById(restaurantId)));
        return repository.save(dish);
    }

    @CacheEvict(value = "dishes", allEntries = true)
    @Transactional
    public void update(Dish dish, int id, int restaurantId) {
        log.info("updateDish restaurantId={} dish={} dishId={}", restaurantId, dish, id);
        assureIdConsistent(dish, id);
        Dish oldDish = checkOptional(get(id, restaurantId));
        oldDish.setDescription(dish.getDescription());
        oldDish.setPrice(dish.getPrice());
    }

    @CacheEvict(value = "dishes", allEntries = true)
    @Transactional
    public void delete(int id, int restaurantId) {
        log.info("deleteDish restaurantId={} dishId={}", restaurantId, id);
        Dish dish = checkOptional(get(id, restaurantId));
        repository.delete(dish);
    }

    public Optional<Menu> getMenu(int restaurantId) {
        return restaurantRepository.findAvailableById(restaurantId)
                .map(r -> new Menu(r, getAll(restaurantId)));
    }

    @Cacheable("dishes")
    @Transactional
    public List<Menu> getAllMenus() {
        Map<Restaurant, List<Dish>> dishesByRestaurant = repository.findAllByDate(today()).stream()
                .collect(Collectors.groupingBy(Dish::getRestaurant));
        return restaurantRepository.getAllByAvailableTrue().stream()
                .map(r -> new Menu(r, dishesByRestaurant.getOrDefault(r, Collections.emptyList())))
                .toList();
    }
}

