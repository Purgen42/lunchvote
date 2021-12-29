package ru.javaops.topjava2.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity (name = "Restaurants")
@Table(name = "restaurants")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)

public class Restaurant extends NamedEntity {
    @Column(name = "address")
    private String address;

    @Column(name = "available")
    private boolean available = true;
}
