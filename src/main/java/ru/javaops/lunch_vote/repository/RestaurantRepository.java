package ru.javaops.lunch_vote.repository;

import org.springframework.data.jpa.repository.EntityGraph;
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

    @EntityGraph(attributePaths = {"dishes"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select r from Restaurants r where r.available = true and r.id = ?1")
    Optional<Restaurant> getWithDishes(int id);

    @EntityGraph(attributePaths = {"dishes"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select r from Restaurants r where r.available = true")
    List<Restaurant> getAllWithDishes();
}
