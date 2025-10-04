package com.thangtq.convo.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "item")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Item extends BaseEntity {
    @Column
    private String name;

    @Column
    private Integer price;

    @Column
    private String description;
}
