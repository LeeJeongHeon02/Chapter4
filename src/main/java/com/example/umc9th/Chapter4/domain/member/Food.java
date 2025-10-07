package com.example.umc9th.Chapter4.domain.member;

import com.example.umc9th.Chapter4.domain.mapping.MemberFood;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "food")
@Getter
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "food_name")
    private String foodName;

    @OneToMany(mappedBy = "food")
    private List<MemberFood> memberFoods = new ArrayList<>();
}
