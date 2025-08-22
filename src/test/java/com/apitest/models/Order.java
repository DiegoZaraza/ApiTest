// src/test/java/com/apitest/models/Order.java
package com.apitest.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long id;
    private Long petId;
    private Integer quantity;
    private String shipDate;
    private String status;
    private Boolean complete;
}