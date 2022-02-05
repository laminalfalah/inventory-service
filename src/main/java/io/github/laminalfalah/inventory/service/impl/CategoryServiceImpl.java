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

import io.github.laminalfalah.inventory.mapper.CategoryMapper;
import io.github.laminalfalah.inventory.model.Category;
import io.github.laminalfalah.inventory.payload.filter.CategoryFilter;
import io.github.laminalfalah.inventory.payload.request.CategoryRequest;
import io.github.laminalfalah.inventory.payload.response.CategoryResponse;
import io.github.laminalfalah.inventory.repository.CategoryRepository;
import io.github.laminalfalah.inventory.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * @author laminalfalah on 25/01/22
 */

@Service
@Validated
public class CategoryServiceImpl extends AbstractBaseService<Category, CategoryRequest, CategoryResponse, String, CategoryFilter> implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        super(categoryRepository, categoryMapper);
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

}
