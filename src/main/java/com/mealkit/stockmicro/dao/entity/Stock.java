package com.mealkit.stockmicro.dao.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "stock")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "ingredient_name")
    private String ingredientName;
    @Column(name = "count")
    private Integer count;
    @Column(name = "unit_of_measure")
    private String unitOfMeasure;

}
