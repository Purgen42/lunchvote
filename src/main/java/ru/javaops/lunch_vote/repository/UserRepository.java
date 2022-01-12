package ru.javaops.lunch_vote.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.lunch_vote.model.User;

import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface UserRepository extends BaseRepository<User> {
    Optional<User> getByEmail(String email);
}