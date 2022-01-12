package ru.javaops.lunch_vote.web.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import ru.javaops.lunch_vote.model.User;
import ru.javaops.lunch_vote.service.UserService;

public abstract class AbstractUserController {

    @Autowired
    protected UserService service;

    @Autowired
    private UniqueMailValidator emailValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(emailValidator);
    }

    public ResponseEntity<User> get(int id) {
        return ResponseEntity.of(service.get(id));
    }

    public void delete(int id) {
        service.delete(id);
    }
}