package ru.javaops.lunch_vote.web.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javaops.lunch_vote.model.Restaurant;
import ru.javaops.lunch_vote.service.RestaurantService;

import java.util.List;

@RestController
@RequestMapping(value = ReadMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@CacheConfig(cacheNames = "restaurants")
public class ReadMenuController {
    static final String REST_URL = "/api/restaurants";

    @Autowired
    private RestaurantService service;

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        return ResponseEntity.of(service.getWithDishes(id));
    }

    @GetMapping
    public List<Restaurant> getAll() {
        return service.getAllWithDishes();
    }
}
