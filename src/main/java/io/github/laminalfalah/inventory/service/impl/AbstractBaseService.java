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

import io.github.laminalfalah.inventory.mapper.BaseMapper;
import io.github.laminalfalah.inventory.model.AuditTrail;
import io.github.laminalfalah.inventory.model.enums.StatusActive;
import io.github.laminalfalah.inventory.payload.Direction;
import io.github.laminalfalah.inventory.payload.Parameter;
import io.github.laminalfalah.inventory.payload.response.base.BaseResponse;
import io.github.laminalfalah.inventory.payload.response.base.MetaResponse;
import io.github.laminalfalah.inventory.payload.response.base.PagingResponse;
import io.github.laminalfalah.inventory.repository.base.BaseRepository;
import io.github.laminalfalah.inventory.service.BaseService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * @author laminalfalah on 25/01/22
 */

@Service
@Validated
@AllArgsConstructor
public abstract class AbstractBaseService<E extends AuditTrail, I, O, K, F> implements BaseService<I, O, K, F> {

    private final BaseRepository<E, K> baseRepository;

    private final BaseMapper<E, I, O> baseMapper;

    @Override
    public BaseResponse<List<O>> findAll(Parameter<F> parameter) {
        var page = buildPage(parameter);
        return BaseResponse.<List<O>>builder()
            .metaResponse(
                MetaResponse.builder()
                    .pagingResponse(buildPagination(page, parameter))
                    .build()
            )
            .data(baseMapper.toResponseList(page.getContent()))
            .build();
    }

    @Override
    public BaseResponse<O> store(I request) {
        return BaseResponse.<O>builder()
            .data(baseMapper.toResponse(
                baseRepository.save(
                    baseMapper.toEntity(request)
                )
            ))
            .build();
    }

    @Override
    public BaseResponse<List<O>> batch(List<I> requests) {
        return BaseResponse.<List<O>>builder()
            .data(baseMapper.toResponseList(
                baseRepository.saveAll(
                    baseMapper.toEntityList(requests)
                )
            ))
            .build();
    }

    @Override
    public BaseResponse<Optional<O>> findById(K id) {
        return BaseResponse.<Optional<O>>builder()
            .data(baseRepository.findByIdAndDeletedAtIsNull(id)
                .map(baseMapper::toResponse)
                .or(Optional::empty)
            )
            .build();
    }

    @Override
    public BaseResponse<O> update(K id, I request) {
        var data = baseRepository.findByIdAndDeletedAtIsNull(id);

        return BaseResponse.<O>builder()
            .data(data.map(e -> baseMapper.toResponse(
                baseRepository.save(
                    baseMapper.toUpdateEntity(e, request)
                )
                )).orElseThrow(() -> new RuntimeException("Data not found"))
            ).build();
    }

    @Override
    public BaseResponse<Optional<String>> delete(K id) {
        return BaseResponse.<Optional<String>>builder()
            .data(baseRepository.findByIdAndDeletedAtIsNull(id)
                .map(entity -> {
                    entity.setDeletedAt(new Date());
                    entity.setDeletedBy("SYSTEM");
                    entity.setActive(StatusActive.DELETED);
                    baseRepository.save(entity);
                    return "Data has been deleted";
                }).or(Optional::empty)
            )
            .build();
    }

    private Page<E> buildPage(Parameter<F> parameter) {
        return baseRepository.findAllByDeletedAtIsNull(
            /*buildSpecification(parameter.getParams()),*/
            buildPageable(parameter)
        );
    }

    private Pageable buildPageable(Parameter<?> parameter) {
        var orders = new ArrayList<Sort.Order>();

        parameter.getSorts().forEach(sort -> orders.add(
            new Sort.Order(
                sort.getDirection().name().equals(Direction.ASC.name()) ? Sort.Direction.ASC : Sort.Direction.DESC,
                sort.getKey()
            )
        ));

        return PageRequest.of(
            parameter.getPage().intValue() - 1,
            parameter.getSize().intValue(),
            Sort.by(orders)
        );
    }

    @SneakyThrows
    private Specification<E> buildSpecification(F filter) {
        return (root, query, cb) -> {
            var predicates = new ArrayList<Predicate>();

            predicates.add(
                cb.and(
                    cb.isNull(
                        root.get("deletedAt")
                    )
                )
            );

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private PagingResponse buildPagination(Page<E> page, Parameter<F> parameter) {
        return PagingResponse.builder()
            .page(page.getNumber() + 1)
            .size(page.getSize())
            .totalPages(page.getTotalPages())
            .totalItems(page.getTotalElements())
            .sorts(parameter.getSorts())
            .params(parameter.getParams())
            .build();
    }

}
