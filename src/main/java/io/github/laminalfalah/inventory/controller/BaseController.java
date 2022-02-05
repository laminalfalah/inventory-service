package io.github.laminalfalah.inventory.controller;

/*
 * Copyright (C) 2022 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.inventory.controller
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

import io.github.laminalfalah.inventory.payload.response.base.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author laminalfalah on 26/01/22
 */

public abstract class BaseController {

    protected BaseController() {

    }

    protected static <X extends BaseResponse<?>> ResponseEntity<BaseResponse<?>> ok(X data) {
        return ok(data, null);
    }

    protected static <X extends BaseResponse<?>> ResponseEntity<BaseResponse<?>> ok(X data, String message) {
        var metaResponse = data.getMetaResponse();
        metaResponse.setCode(HttpStatus.OK.value());
        metaResponse.setMessage(message == null ? HttpStatus.OK.getReasonPhrase() : message);
        metaResponse.setTimestamp(System.currentTimeMillis());
        data.setMetaResponse(metaResponse);

        return ResponseEntity.status(metaResponse.getCode()).body(data);
    }

    protected static <X extends BaseResponse<?>> ResponseEntity<BaseResponse<?>> error(HttpStatus status, X data) {
        return error(status, null, data);
    }

    protected static <X extends BaseResponse<?>> ResponseEntity<BaseResponse<?>> error(HttpStatus status, String message, X data) {
        var metaResponse = data.getMetaResponse();
        metaResponse.setCode(status.value());
        metaResponse.setMessage(message == null ? status.getReasonPhrase() : message);
        data.setMetaResponse(metaResponse);

        return ResponseEntity.status(metaResponse.getCode()).body(data);
    }

}
