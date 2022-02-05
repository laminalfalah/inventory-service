package io.github.laminalfalah.inventory.utils;

/*
 * Copyright (C) 2022 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.inventory.utils
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
import io.github.laminalfalah.inventory.payload.Direction;
import io.github.laminalfalah.inventory.payload.Parameter;
import io.github.laminalfalah.inventory.payload.QueryParameterField;
import io.github.laminalfalah.inventory.payload.Sort;
import io.github.laminalfalah.inventory.properties.PagingProperties;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.core.MethodParameter;

/**
 * @author laminalfalah on 30/01/22
 */

public final class QueryParameterUtils {

    public static final String SORT_BY_SEPARATOR = ":";
    public static final String SORT_BY_SPLITTER = ";";
    public static final String EMPTY_STRING = "";

    private QueryParameterUtils() {
    }

    public static List<Sort> getSortByList(String value, PagingProperties properties) {
        return StringUtils.isEmpty(value) ? Collections.emptyList() : toSortByList(value, properties);
    }

    public static Long getLong(String value, Long defaultValue) {
        return value == null ? defaultValue : toLong(value, defaultValue);
    }

    public static void setValueString(Class<?> type, Parameter<?> parameter, QueryParameterField parameterField, String value) {
        if (type == Object.class) {
            parameterField.setValue(parameter.getParams(), value);
        } else if (type == Character.class || type == Character.TYPE) {
            parameterField.setValue(parameter.getParams(), value.charAt(0));
        } else if (type == String.class) {
            parameterField.setValue(parameter.getParams(), defaultValue(value, parameterField, parameter));
        } else if (type == Boolean.class || type == Boolean.TYPE) {
            parameterField.setValue(parameter.getParams(), defaultValueBoolean(value));
        } else {
            setValueNumber(type, parameter, parameterField, value);
        }
    }

    private static void setValueNumber(Class<?> type, Parameter<?> parameter, QueryParameterField parameterField, String value) {
        if (type == Byte.class || type == Byte.TYPE) {
            parameterField.setValue(parameter.getParams(), defaultValue(value, parameterField, parameter, Byte::parseByte));
        } else if (type == Short.class || type == Short.TYPE) {
            parameterField.setValue(parameter.getParams(), defaultValue(value, parameterField, parameter, Short::parseShort));
        } else if (type == Integer.class || type == Integer.TYPE) {
            parameterField.setValue(parameter.getParams(), defaultValue(value, parameterField, parameter, Integer::parseInt));
        } else if (type == Long.class || type == Long.TYPE || type == Number.class) {
            parameterField.setValue(parameter.getParams(), defaultValue(value, parameterField, parameter, Long::parseLong));
        } else if (type == Double.class || type == Double.TYPE) {
            parameterField.setValue(parameter.getParams(), defaultValue(value, parameterField, parameter, Double::parseDouble));
        } else if (type == Float.class || type == Float.TYPE) {
            parameterField.setValue(parameter.getParams(), defaultValue(value, parameterField, parameter, Float::parseFloat));
        } else {
            setValueDateTime(type, parameter, parameterField, value);
        }
    }

    @SneakyThrows
    private static void setValueDateTime(Class<?> type, Parameter<?> parameter, QueryParameterField parameterField, String value) {
        if (type == Date.class) {
            parameterField.setValue(parameter.getParams(), DateUtils.parseDate(defaultValueDate(value), parameterField.getDateFormatter()));
        } else if (type == LocalDate.class) {
            var localDate = DateUtils.parseDate(defaultValueDate(value), parameterField.getDateFormatter())
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            parameterField.setValue(parameter.getParams(), localDate);
        } else if (type == LocalDateTime.class) {
            var localDateTime = DateUtils.parseDate(defaultValueDateTime(value), parameterField.getDateTimeFormatter())
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            parameterField.setValue(parameter.getParams(), localDateTime);
        } else if (type == LocalTime.class) {
            var localTime = DateUtils.parseDate(defaultValueTime(value), parameterField.getTimeFormatter())
                .toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
            parameterField.setValue(parameter.getParams(), localTime);
        }
    }

    private static List<Sort> toSortByList(String request, PagingProperties properties) {
        return Arrays.stream(request.split(SORT_BY_SPLITTER))
            .map(s -> toSortBy(s, properties))
            .filter(Objects::nonNull)
            .filter(sortBy -> Objects.nonNull(sortBy.getKey()))
            .collect(Collectors.toList());
    }

    private static Sort toSortBy(String request, PagingProperties properties) {
        String sort = request.trim();

        if (StringUtils.isEmpty(sort.replace(SORT_BY_SEPARATOR, EMPTY_STRING)) || sort.startsWith(SORT_BY_SEPARATOR)) {
            return null;
        }

        String[] sortBy = sort.split(SORT_BY_SEPARATOR);

        return new Sort(
            getAt(sortBy, 0, null),
            EnumUtils.getEnum(Direction.class,
                getAt(sortBy, 1, properties.getDefaultDirection().name()).toUpperCase()
            )
        );
    }

    private static String getAt(String[] strings, int index, String defaultValue) {
        return strings.length <= index ? defaultValue : strings[index];
    }

    private static Long toLong(String value, Long defaultValue) {
        try {
            return Long.valueOf(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static <T> void addDefaultFilter(Parameter<T> filter, String field) {
        addDefaultFilter(filter, field, Direction.ASC);
    }

    public static <T> void addDefaultFilter(Parameter<T> filter, String field, Direction direction) {
        var sortByList = new ArrayList<Sort>();
        sortByList.add(new Sort(field, direction));
        filter.setSorts(sortByList);
    }

    public static String valueOf(Map<String, Object> params, QueryParameterField field, QueryParameter annotation) {
        return (String) params.get(field.getParameter(annotation));
    }

    @SneakyThrows
    public static Class<?> genericClassName(MethodParameter methodParameter) {
        var type = ((ParameterizedType) methodParameter.getGenericParameterType()).getActualTypeArguments()[0];
        var className = type.getTypeName();
        return Class.forName(className);
    }

    public static boolean excludeDefaultPaging(String key, PagingProperties pagingProperties) {
        return !key.equalsIgnoreCase(pagingProperties.getQuery().getPageKey()) &&
            !key.equalsIgnoreCase(pagingProperties.getQuery().getSizeKey()) &&
            !key.equalsIgnoreCase(pagingProperties.getQuery().getSortKey());
    }

    public static String defaultValue(String value, QueryParameterField filterField, Parameter<?> filter) {
        return value != null && !StringUtils.isEmpty(value.trim()) ? value : defaultValueGetter(filterField, filter);
    }

    public static <T> T defaultValue(String value, QueryParameterField filterField, Parameter<?> filter, Function<String, T> func) {
        return value == null ? defaultValueGetter(filterField, filter) : func.apply(validateNumberNotNull(value));
    }

    public static Boolean defaultValueBoolean(String value) {
        if (value != null && !StringUtils.isEmpty(value)) {
            return value.equalsIgnoreCase("true") || value.equalsIgnoreCase("1");
        } else {
            return value == null ? null : false;
        }
    }

    public static String defaultValueDate(String value) {
        return value != null && !StringUtils.isEmpty(value.trim()) ? value : "0000-00-00";
    }

    public static String defaultValueDateTime(String value) {
        return value != null && !StringUtils.isEmpty(value.trim()) ? value : "0000-00-00 00:00:00";
    }

    public static String defaultValueTime(String value) {
        return value != null && !StringUtils.isEmpty(value.trim()) ? value : "00:00:00";
    }

    @SuppressWarnings("unchecked")
    private static <T> T defaultValueGetter(QueryParameterField parameterField, Parameter<?> parameter) {
        return parameterField.getValue(parameter.getParams()) != null ? (T) parameterField.getValue(parameter.getParams()) : null;
    }

    private static String validateNumberNotNull(String value) {
        return value != null && value.equalsIgnoreCase("null") ? "0" : defaultZeroIfNotNull(value);
    }

    private static String defaultZeroIfNotNull(String value) {
        return !NumberUtils.isCreatable(value) ? "0" : value;
    }
}
