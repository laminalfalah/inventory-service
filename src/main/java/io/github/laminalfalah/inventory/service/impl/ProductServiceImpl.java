package io.github.laminalfalah.inventory.service.impl;

/*
 * Copyright (C) 2022 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.inventory.service.impl
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

import io.github.laminalfalah.inventory.mapper.ProductMapper;
import io.github.laminalfalah.inventory.model.Product;
import io.github.laminalfalah.inventory.payload.filter.ProductFilter;
import io.github.laminalfalah.inventory.payload.request.ProductRequest;
import io.github.laminalfalah.inventory.payload.response.ProductResponse;
import io.github.laminalfalah.inventory.repository.ProductRepository;
import io.github.laminalfalah.inventory.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * @author laminalfalah on 25/01/22
 */

@Service
@Validated
public class ProductServiceImpl extends AbstractBaseService<Product, ProductRequest, ProductResponse, String, ProductFilter> implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        super(productRepository, productMapper);
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }
}
