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

import io.github.laminalfalah.inventory.exception.MethodHandleException;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import lombok.SneakyThrows;

/**
 * @author laminalfalah on 30/01/22
 */

public final class ReflectionUtils {

    private ReflectionUtils() {
    }

    @SneakyThrows
    public static Method getMethodName(Field field, MethodName methodNameEnum) {
        Method method;
        try {
            if (methodNameEnum == MethodName.SETTER) {
                method = new PropertyDescriptor(field.getName(), field.getDeclaringClass()).getWriteMethod();
            } else {
                method = new PropertyDescriptor(field.getName(), field.getDeclaringClass()).getReadMethod();
            }
        } catch (IntrospectionException e) {
            String methodName = Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
            method = field.getDeclaringClass().getMethod("get" + methodName);
        }
        return method;
    }

    public static MethodHandle findGetter(Field field) {
        try {
            var method = getMethodName(field, MethodName.GETTER);

            var getter = method == null ? null : MethodHandles.lookup().unreflect(method);

            if (getter == null) {
                throw new MethodHandleException(
                    String.format("No getter specified with field '%s' for class '%s'",
                        field.getName(),
                        field.getDeclaringClass()
                    )
                );
            } else {
                return getter;
            }
        } catch (IllegalAccessException e) {
            throw new MethodHandleException(
                String.format("Unable to access getter specified with field '%s' for class '%s'",
                    field.getName(),
                    field.getDeclaringClass()
                ), e);
        }
    }

    public static MethodHandle findSetter(Field field) {
        try {
            var method = getMethodName(field, MethodName.SETTER);

            var setter = method == null ? null : MethodHandles.lookup().unreflect(method);

            if (setter == null) {
                throw new MethodHandleException(
                    String.format("No setter specified with field '%s' for class '%s'",
                        field.getName(),
                        field.getDeclaringClass()
                    )
                );
            } else {
                return setter;
            }
        } catch (IllegalAccessException e) {
            throw new MethodHandleException(
                String.format("Unable to access setter specified with field '%s' for class '%s'",
                    field.getName(),
                    field.getDeclaringClass()
                ), e);
        }
    }

    public enum MethodName {
        GETTER, SETTER
    }

}
