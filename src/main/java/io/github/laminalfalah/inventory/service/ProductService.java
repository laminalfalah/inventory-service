package io.github.laminalfalah.inventory.service;

/*
 * Copyright (C) 2022 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.inventory.service
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

import io.github.laminalfalah.inventory.payload.filter.ProductFilter;
import io.github.laminalfalah.inventory.payload.request.ProductRequest;
import io.github.laminalfalah.inventory.payload.response.ProductResponse;

/**
 * @author laminalfalah on 25/01/22
 */

public interface ProductService extends BaseService<ProductRequest, ProductResponse, String, ProductFilter> {

}