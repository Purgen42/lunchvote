package com.github.purgen42.lunchvote.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.github.purgen42.lunchvote.model.Dish;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface DishRepository extends BaseRepository<Dish> {
    List<Dish> findAllByDate(LocalDate date);

    List<Dish> findAllByDateAndRestaurantId(LocalDate date, int restaurantId);

    Optional<Dish> findByIdAndDateAndRestaurantId(int id, LocalDate date, int restaurantId);
}
