package com.mealkit.stockmicro.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestStock {
    private Long ingredientId;
    private Integer count;
}
