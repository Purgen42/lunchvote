package com.github.purgen42.lunchvote.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.github.purgen42.lunchvote.model.Restaurant;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface RestaurantRepository extends BaseRepository<Restaurant> {
    List<Restaurant> getAllByAvailableTrue();

    @Query("select r from Restaurants r where r.id = ?1 and r.available = true ")
    Optional<Restaurant> findAvailableById(Integer integer);
}
