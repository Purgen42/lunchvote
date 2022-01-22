package com.github.purgen42.lunchvote.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.github.purgen42.lunchvote.model.User;

import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface UserRepository extends BaseRepository<User> {
    Optional<User> getByEmail(String email);
}