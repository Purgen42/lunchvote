package ru.javaops.lunch_vote.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.lunch_vote.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface VoteRepository extends BaseRepository<Vote> {
    @Query("select v from Vote v where v.date = ?1 and v.restaurant.available = true")
    List<Vote> getAllByDate(LocalDate date);

    Optional<Vote> findByUserIdAndDate(Integer userId, LocalDate date);

    List<Vote> getAllByRestaurantIdAndDate(int restaurantId, LocalDate date);
}
