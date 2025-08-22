package com.apitest.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiResponse {
    private Integer code;
    private String type;
    private String message;
}