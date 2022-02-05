package io.github.laminalfalah.inventory.resolver;

/*
 * Copyright (C) 2022 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.inventory.resolver
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

import io.github.laminalfalah.inventory.annotation.QueryParameter;
import io.github.laminalfalah.inventory.payload.Parameter;
import io.github.laminalfalah.inventory.payload.QueryParameterField;
import io.github.laminalfalah.inventory.properties.PagingProperties;
import io.github.laminalfalah.inventory.utils.QueryParameterUtils;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * @author laminalfalah on 30/01/22
 */

@RequiredArgsConstructor
public class QueryParameterMapper {

    private final PagingProperties properties;

    @SneakyThrows
    public Parameter<Object> map(MethodParameter parameter, Logger logger, NativeWebRequest exchange) {
        var request = exchange.getNativeRequest(HttpServletRequest.class);

        var clazz = QueryParameterUtils.genericClassName(parameter);

        var parameters = new Parameter<>();

        parameters.setParams(clazz.getConstructor().newInstance());

        if (request != null) {
            setFilterParams(parameters, request);

            defaultFilter(parameters, request);

            if (properties.getLogEnabled() != null && properties.getLogEnabled()) {
                logger.info("Query Parameter: {}", parameters);
            }
        }

        return parameters;
    }

    private Map<String, Object> properties(HttpServletRequest request) {
        var params = new ConcurrentHashMap<String, Object>();

        request.getParameterMap().forEach((key, value) -> {
            if (QueryParameterUtils.excludeDefaultPaging(key, properties) && value != null) {
                params.put(key, value[0]);
            }
        });

        return params;
    }

    private void defaultFilter(Parameter<?> parameter, HttpServletRequest request) {
        parameter.setPage(QueryParameterUtils.getLong(
            request.getParameter(properties.getQuery().getPageKey()),
            properties.getDefaultPage()
        ));

        parameter.setSize(QueryParameterUtils.getLong(
            request.getParameter(properties.getQuery().getSizeKey()),
            properties.getDefaultSize()
        ));

        if (parameter.getSize() > properties.getMaxSizePerPage()) {
            parameter.setSize(properties.getMaxSizePerPage());
        }

        parameter.setSorts(QueryParameterUtils.getSortByList(
            request.getParameter(properties.getQuery().getSortKey()),
            properties
        ));
    }

    private void setFilterParams(Parameter<?> parameter, HttpServletRequest request) {
        for (var field: parameter.getParams().getClass().getDeclaredFields()) {
            var queryParameter = field.getAnnotation(QueryParameter.class);

            var parameterField = QueryParameterField.builder()
                .field(field)
                .annotation(queryParameter)
                .build();

            var type = parameterField.getFieldType();

            QueryParameterUtils.setValueString(
                type,
                parameter,
                parameterField,
                setValueOf(request, parameterField, queryParameter)
            );
        }
    }

    private String setValueOf(HttpServletRequest request, QueryParameterField parameterField, QueryParameter column) {
        return QueryParameterUtils.valueOf(properties(request), parameterField, column);
    }

}
