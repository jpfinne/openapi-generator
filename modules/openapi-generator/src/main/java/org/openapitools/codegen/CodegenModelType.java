/*
 * Copyright 2018 OpenAPI-Generator Contributors (https://openapi-generator.tech)
 * Copyright 2018 SmartBear Software
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openapitools.codegen;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

public enum CodegenModelType {

    MODEL(CodegenModel::new),
    OPERATION(CodegenOperation::new),
    PARAMETER(CodegenParameter::new),
    PROPERTY(CodegenProperty::new),
    RESPONSE(CodegenResponse::new),
    SECURITY(CodegenSecurity::new);

//    private final Class<?> defaultImplementation;

    private Supplier<Object> supplier;

    private CodegenModelType(Supplier<Object> supplier) {
        this.supplier = supplier;
    }
//    private Supplier<Object> supplier = () ->
//    {
//        try {
//            return getDefaultImplementation().getDeclaredConstructor().newInstance();
//        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
//            throw new RuntimeException(e);
//        }
//    };

//    private CodegenModelType(Class<?> defaultImplementation) {
//        this.defaultImplementation = defaultImplementation;
//    }

//    public Class<?> getDefaultImplementation() {
//        return defaultImplementation;
//    }


    public void setSupplier(Supplier<Object> supplier) {
        this.supplier = supplier;
    }

    public Supplier<Object> getSupplier() {
        return supplier;
    }
}
