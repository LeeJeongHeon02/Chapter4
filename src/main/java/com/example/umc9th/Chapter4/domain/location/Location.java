package com.example.umc9th.Chapter4.domain.location;

import com.example.umc9th.Chapter4.domain.restaurant.Restaurant;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "location")
@NoArgsConstructor
@Data
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name; // 지역명

    // Restaurant와 1:N 관계 (한 지역에 여러 레스토랑)
    @OneToMany(mappedBy = "location")
    private List<Restaurant> restaurants = new ArrayList<>();
}
