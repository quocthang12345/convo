package com.thangtq.convo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateItemRequest {
    private String name;
    private String description;
    private Integer price;
}
