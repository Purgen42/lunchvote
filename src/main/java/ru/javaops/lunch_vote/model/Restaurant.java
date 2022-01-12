package ru.javaops.lunch_vote.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Restaurants")
@Table(name = "restaurants")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)

public class Restaurant extends NamedEntity {
    @Column(name = "address")
    private String address = "";

    @Column(name = "available", nullable = false, columnDefinition = "bool default true")
    private boolean available = true;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ToString.Exclude
    private List<Dish> dishes;

    public Restaurant(Integer id, String name, String address, boolean available) {
        super(id, name);
        this.address = address;
        this.available = available;
    }

    public Restaurant(Integer id, String name, String address) {
        super(id, name);
        this.address = address;
        this.available = true;
    }
}
