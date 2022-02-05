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

import io.github.laminalfalah.inventory.mapper.PermissionMapper;
import io.github.laminalfalah.inventory.model.Permission;
import io.github.laminalfalah.inventory.payload.filter.PermissionFilter;
import io.github.laminalfalah.inventory.payload.request.PermissionRequest;
import io.github.laminalfalah.inventory.payload.response.PermissionResponse;
import io.github.laminalfalah.inventory.payload.response.base.BaseResponse;
import io.github.laminalfalah.inventory.repository.PermissionRepository;
import io.github.laminalfalah.inventory.service.PermissionService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * @author laminalfalah on 25/01/22
 */

@Service
@Validated
public class PermissionServiceImpl extends AbstractBaseService<Permission, PermissionRequest, PermissionResponse, String, PermissionFilter> implements PermissionService {

    private final PermissionRepository permissionRepository;

    private final PermissionMapper permissionMapper;

    @Autowired
    public PermissionServiceImpl(PermissionRepository permissionRepository, PermissionMapper permissionMapper) {
        super(permissionRepository, permissionMapper);
        this.permissionRepository = permissionRepository;
        this.permissionMapper = permissionMapper;
    }

    @Override
    public BaseResponse<Optional<String>> delete(String id) {
        var allowDeleted = permissionRepository.findById(id).map(Permission::isAllowDeleted).orElse(false);

        if (Boolean.TRUE.equals(allowDeleted)) {
            return super.delete(id);
        }

        throw new IllegalStateException("Permission is not allow deleted");
    }

}
