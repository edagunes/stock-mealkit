package com.mealkit.stockmicro.dao.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String ingredientName;
    @Column
    private Integer count;
    @Column
    private String unitOfMeasure;

}
