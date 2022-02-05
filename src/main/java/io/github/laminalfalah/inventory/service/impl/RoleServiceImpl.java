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

import io.github.laminalfalah.inventory.mapper.RoleMapper;
import io.github.laminalfalah.inventory.model.Role;
import io.github.laminalfalah.inventory.payload.filter.RoleFilter;
import io.github.laminalfalah.inventory.payload.request.RoleRequest;
import io.github.laminalfalah.inventory.payload.response.RoleResponse;
import io.github.laminalfalah.inventory.payload.response.base.BaseResponse;
import io.github.laminalfalah.inventory.repository.RoleRepository;
import io.github.laminalfalah.inventory.service.RoleService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * @author laminalfalah on 25/01/22
 */

@Service
@Validated
public class RoleServiceImpl extends AbstractBaseService<Role, RoleRequest, RoleResponse, String, RoleFilter> implements RoleService {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        super(roleRepository, roleMapper);
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public BaseResponse<Optional<String>> delete(String id) {
        var allowDeleted = roleRepository.findById(id).map(Role::isAllowDeleted).orElse(false);

        if (Boolean.TRUE.equals(allowDeleted)) {
            return super.delete(id);
        }

        throw new IllegalStateException("Role is not allow deleted");
    }
}
