package com.github.purgen42.lunchvote.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import com.github.purgen42.lunchvote.model.User;
import com.github.purgen42.lunchvote.service.UserService;

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