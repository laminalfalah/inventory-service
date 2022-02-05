package io.github.laminalfalah.inventory.repository.base;

/*
 * Copyright (C) 2022 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.inventory.repository.base
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

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author laminalfalah on 25/01/22
 */

@NoRepositoryBean
public interface BaseRepository<T, X> extends JpaRepository<T, X>, JpaSpecificationExecutor<T> {

    Optional<T> findByIdAndDeletedAtIsNull(X id);

    Page<T> findAllByDeletedAtIsNull(Pageable pageable);

    Page<T> findAllByDeletedAtIsNull(Specification<T> spec, Pageable pageable);

}
