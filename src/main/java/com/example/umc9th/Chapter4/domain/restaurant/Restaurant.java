package com.example.umc9th.Chapter4.domain.restaurant;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "restaurant")
@Getter
@NoArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "restaurant_name")
    private String restaurantName;

    @Column(name = "restaurant_address")
    private String restaurantAddress;

    @Column(name = "restaurant_runtime")
    private LocalDateTime restaurantRuntime;
}
