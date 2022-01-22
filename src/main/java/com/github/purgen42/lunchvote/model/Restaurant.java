package com.github.purgen42.lunchvote.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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
