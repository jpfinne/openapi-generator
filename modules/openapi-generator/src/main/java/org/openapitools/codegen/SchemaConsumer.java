package org.openapitools.codegen;

import io.swagger.v3.oas.models.media.Schema;

import java.util.function.Consumer;

@FunctionalInterface
public interface SchemaConsumer extends Consumer<Schema> {


    static void acceptSchema(Object codegen, Schema schema) {
        if (codegen instanceof SchemaConsumer) {
            ((SchemaConsumer) codegen).accept(schema);
        }
    }
}
