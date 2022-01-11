package ru.javaops.lunch_vote.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.lunch_vote.model.Dish;

@Transactional
@Repository
public interface DishRepository extends BaseRepository<Dish> {
}
