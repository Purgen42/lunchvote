package com.github.purgen42.lunchvote.service;

import com.github.purgen42.lunchvote.model.Restaurant;
import com.github.purgen42.lunchvote.repository.DishRepository;
import com.github.purgen42.lunchvote.repository.RestaurantRepository;
import com.github.purgen42.lunchvote.to.Menu;
import com.github.purgen42.lunchvote.util.DateTimeUtil;
import com.github.purgen42.lunchvote.util.validation.ValidationUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.purgen42.lunchvote.model.Dish;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
@CacheConfig(cacheNames = "dishes")
public class DishService {
    private final DishRepository repository;
    private final RestaurantRepository restaurantRepository;

    public Optional<Dish> get(int id, int restaurantId) {
        log.info("get id={} restaurantId={}", id, restaurantId);
        return repository.findByIdAndDateAndRestaurantId(id, DateTimeUtil.today(), restaurantId);
    }

    public List<Dish> getAll(int restaurantId) {
        log.info("getAll restaurantId={}", restaurantId);
        return repository.findAllByDateAndRestaurantId(DateTimeUtil.today(), restaurantId);
    }

    @CacheEvict(value = "dishes", allEntries = true)
    public Dish create(Dish dish, int restaurantId) {
        log.info("add  dish={} restaurantId={}\", id, restaurantId", dish, restaurantId);
        ValidationUtil.checkNew(dish);
        dish.setDate(DateTimeUtil.today());
        dish.setRestaurant(ValidationUtil.checkOptional(restaurantRepository.findById(restaurantId)));
        return repository.save(dish);
    }

    @CacheEvict(value = "dishes", allEntries = true)
    @Transactional
    public void update(Dish dish, int id, int restaurantId) {
        log.info("updateDish restaurantId={} dish={} dishId={}", restaurantId, dish, id);
        ValidationUtil.assureIdConsistent(dish, id);
        Dish oldDish = ValidationUtil.checkOptional(get(id, restaurantId));
        oldDish.setDescription(dish.getDescription());
        oldDish.setPrice(dish.getPrice());
    }

    @CacheEvict(value = "dishes", allEntries = true)
    @Transactional
    public void delete(int id, int restaurantId) {
        log.info("deleteDish restaurantId={} dishId={}", restaurantId, id);
        Dish dish = ValidationUtil.checkOptional(get(id, restaurantId));
        repository.delete(dish);
    }

    public Optional<Menu> getMenu(int restaurantId) {
        return restaurantRepository.findAvailableById(restaurantId)
                .map(r -> new Menu(r, getAll(restaurantId)));
    }

    @Cacheable("dishes")
    @Transactional
    public List<Menu> getAllMenus() {
        Map<Restaurant, List<Dish>> dishesByRestaurant = repository.findAllByDate(DateTimeUtil.today()).stream()
                .collect(Collectors.groupingBy(Dish::getRestaurant));
        return restaurantRepository.getAllByAvailableTrue().stream()
                .map(r -> new Menu(r, dishesByRestaurant.getOrDefault(r, Collections.emptyList())))
                .toList();
    }
}

