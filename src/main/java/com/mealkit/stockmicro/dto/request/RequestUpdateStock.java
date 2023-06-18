package com.mealkit.stockmicro.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestUpdateStock {
    private Long id;
    private Integer count;
}
