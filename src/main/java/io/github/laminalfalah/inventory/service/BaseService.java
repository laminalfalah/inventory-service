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

import io.github.laminalfalah.inventory.payload.Parameter;
import io.github.laminalfalah.inventory.payload.response.base.BaseResponse;
import java.util.List;
import java.util.Optional;

/**
 * @author laminalfalah on 25/01/22
 */

public interface BaseService<I, O, K, F> {

    BaseResponse<List<O>> findAll(Parameter<F> parameter);

    BaseResponse<O> store(I request);

    BaseResponse<List<O>> batch(List<I> requests);

    BaseResponse<Optional<O>> findById(K id);

    BaseResponse<O> update(K id, I request);

    BaseResponse<Optional<String>> delete(K id);

}
