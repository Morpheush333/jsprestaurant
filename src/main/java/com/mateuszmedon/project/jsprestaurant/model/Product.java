package com.mateuszmedon.project.jsprestaurant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product implements BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;

    private String description;

    @Column(nullable = false)
    private Double value;

    private int amount;

    @ManyToOne
    @ToString.Exclude
    private Order order;


}
