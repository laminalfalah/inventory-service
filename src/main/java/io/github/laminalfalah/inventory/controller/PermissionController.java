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

import io.github.laminalfalah.inventory.payload.Parameter;
import io.github.laminalfalah.inventory.payload.filter.PermissionFilter;
import io.github.laminalfalah.inventory.payload.request.PermissionRequest;
import io.github.laminalfalah.inventory.service.PermissionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author laminalfalah on 05/02/22
 */

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@Tag(name = "Permission", description = "Permission Controller")
public class PermissionController extends BaseController {

    private final PermissionService service;

    @GetMapping
    public ResponseEntity<?> index(Parameter<PermissionFilter> filter) {
        return ok(service.findAll(filter));
    }

    @PostMapping
    public ResponseEntity<?> store(@Valid @RequestBody PermissionRequest request) {
        return ok(service.store(request));
    }

    @PostMapping("/batch")
    public ResponseEntity<?> batch(@Valid @RequestBody List<PermissionRequest> requests) {
        return ok(service.batch(requests));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable("id") String id) {
        return ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id, @Valid @RequestBody PermissionRequest request) {
        return ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> destroy(@PathVariable("id") String id) {
        return ok(service.delete(id));
    }

}
