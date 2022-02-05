package io.github.laminalfalah.inventory.model;

/*
 * Copyright (C) 2022 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.inventory.model
 *
 * This is part of the inventory.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * @author laminalfalah on 25/01/22
 */

@Setter
@Getter
@SuperBuilder
@RequiredArgsConstructor
@ToString
@Entity
@Table(name = "products")
public class Product extends AuditTrail {

    @Column(name = "parent_id", columnDefinition = "UUID", length = 36)
    private String parentId;

    @Column(name = "user_id", nullable = false, columnDefinition = "UUID", length = 36)
    private String userId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "price", nullable = false, columnDefinition = "NUMERIC(10,2)", length = 10)
    private double price;

    @Column(name = "stock", nullable = false)
    private int stock;

}
