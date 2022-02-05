package io.github.laminalfalah.inventory.payload;

/*
 * Copyright (C) 2022 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.inventory.payload
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
import io.github.laminalfalah.inventory.utils.ReflectionUtils;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.Field;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

/**
 * @author laminalfalah on 31/01/22
 */

public class QueryParameterField {

    @Getter
    private final QueryParameter annotation;

    @Getter
    private final Field field;

    @Getter
    private final MethodHandle getter;

    @Getter
    private final MethodHandle setter;

    @Getter
    private final String parameter;

    @Getter
    private final String dateTimeFormatter;

    @Getter
    private final String dateFormatter;

    @Getter
    private final String timeFormatter;

    @Getter
    private final Class<?> fieldType;

    public static class Builder {

        @Getter
        private QueryParameter annotation;

        @Getter
        private Field field;

        public Builder annotation(QueryParameter annotation) {
            this.annotation = annotation;
            return this;
        }

        public Builder field(Field field) {
            this.field = field;
            return this;
        }

        public QueryParameterField build() {
            return new QueryParameterField(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public QueryParameterField(Builder builder) {
        this.annotation = builder.annotation;
        this.field = builder.field;
        this.getter = ReflectionUtils.findGetter(field);
        this.setter = ReflectionUtils.findSetter(field);
        this.parameter = getParameter(annotation);
        this.dateTimeFormatter = getDateTimeFormat(annotation);
        this.dateFormatter = getDateFormat(annotation);
        this.timeFormatter = getTimeFormat(annotation);
        this.fieldType = getFieldType(annotation);
    }

    @SneakyThrows
    public void setValue(Object obj, Object value) {
        this.setter.invoke(obj, value);
    }

    @SneakyThrows
    public Object getValue(Object obj) {
        return this.getter.invoke(obj);
    }

    public Class<?> getFieldType() {
        return this.field.getType();
    }

    public String getFieldName() {
        return this.field.getName();
    }

    public String getParameter(QueryParameter annotation) {
        if (annotation == null) {
            return getFieldName();
        } else if (!StringUtils.isEmpty(annotation.parameter().trim())) {
            return annotation.parameter().trim();
        } else {
            return StringUtils.isEmpty(annotation.value().trim()) ? getFieldName() : annotation.value().trim();
        }
    }

    private String getDateFormat(QueryParameter annotation) {
        return annotation == null || StringUtils.isEmpty(annotation.dateFormat().trim())
            ? "yyyy-MM-dd"
            : annotation.dateFormat();
    }

    private String getDateTimeFormat(QueryParameter annotation) {
        return annotation == null || StringUtils.isEmpty(annotation.dateTimeFormat().trim())
            ? "yyyy-MM-dd HH:mm:ss"
            : annotation.dateTimeFormat();
    }

    private String getTimeFormat(QueryParameter annotation) {
        return annotation == null || StringUtils.isEmpty(annotation.timeFormat().trim())
            ? "HH:mm:ss"
            : annotation.timeFormat();
    }

    private Class<?> getFieldType(QueryParameter annotation) {
        return annotation == null || annotation.enumClass() == null
            ? null
            : annotation.enumClass();
    }

}
