package io.github.laminalfalah.inventory.annotation;

/*
 * Copyright (C) 2022 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.inventory.annotation
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

import io.github.laminalfalah.inventory.model.enums.ImplicitEnum;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author laminalfalah on 29/01/22
 */

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryParameter {

    String value() default "";

    String parameter() default "";

    String key() default "";

    String fieldSql() default "";

    boolean isCompare() default false;

    Class<? extends Enum<?>> enumClass() default ImplicitEnum.class;

    String dateFormat() default "yyyy-MM-dd";

    String dateTimeFormat() default "yyyy-MM-dd HH:mm:ss";

    String timeFormat() default "HH:mm:ss";

}
