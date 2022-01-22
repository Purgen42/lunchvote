package com.github.purgen42.lunchvote.web.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.github.purgen42.lunchvote.service.DishService;
import com.github.purgen42.lunchvote.to.Menu;

import java.util.List;

@RestController
@RequestMapping(value = MenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@CacheConfig(cacheNames = "restaurants")
public class MenuController {
    static final String REST_URL = "/api/menus";

    @Autowired
    private DishService service;

    @GetMapping("/{id}")
    public ResponseEntity<Menu> get(@PathVariable int id) {
        return ResponseEntity.of(service.getMenu(id));
    }

    @GetMapping
    public List<Menu> getAll() {
        return service.getAllMenus();
    }
}
