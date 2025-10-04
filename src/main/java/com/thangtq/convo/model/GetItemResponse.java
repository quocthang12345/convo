package com.thangtq.convo.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class GetItemResponse extends Base {
    private String name;
    private String description;
    private Integer price;
}
