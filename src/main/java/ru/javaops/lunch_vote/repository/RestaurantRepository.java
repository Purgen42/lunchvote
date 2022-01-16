package ru.javaops.lunch_vote.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.lunch_vote.model.Restaurant;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface RestaurantRepository extends BaseRepository<Restaurant> {
    List<Restaurant> getAllByAvailableTrue();

    @Query("select r from Restaurants r where r.id = ?1 and r.available = true ")
    Optional<Restaurant> findAvailableById(Integer integer);
}
