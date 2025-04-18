/*
 * Copyright 2021 OpenAPI-Generator Contributors (https://openapi-generator.tech)
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

package org.openapitools.codegen.dart.dio;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.*;
import org.openapitools.codegen.*;
import org.openapitools.codegen.languages.DartDioClientCodegen;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@SuppressWarnings("static-method")
public class DartDioModelTest {

    @Test(description = "convert a simple model")
    public void simpleModelTest() {
        final Schema model = new Schema()
                .description("a sample model")
                .addProperties("id", new IntegerSchema())
                .addProperties("name", new StringSchema())
                .addProperties("createdAt", new DateTimeSchema())
                .addRequiredItem("id")
                .addRequiredItem("name");
        final DefaultCodegen codegen = new DartDioClientCodegen();
        OpenAPI openAPI = TestUtils.createOpenAPIWithOneSchema("sample", model);
        codegen.setOpenAPI(openAPI);
        final CodegenModel cm = codegen.fromModel("sample", model);

        Assert.assertEquals(cm.name, "sample");
        Assert.assertEquals(cm.classname, "Sample");
        Assert.assertEquals(cm.description, "a sample model");
        Assert.assertEquals(cm.vars.size(), 3);
        // {{imports}} is not used in template
        //Assert.assertEquals(cm.imports.size(), 1);

        final CodegenProperty property1 = cm.vars.get(0);
        Assert.assertEquals(property1.baseName, "id");
        Assert.assertEquals(property1.dataType, "int");
        Assert.assertEquals(property1.name, "id");
        Assert.assertNull(property1.defaultValue);
        Assert.assertEquals(property1.baseType, "int");
        Assert.assertTrue(property1.required);
        Assert.assertTrue(property1.isPrimitiveType);
        Assert.assertFalse(property1.isContainer);

        final CodegenProperty property2 = cm.vars.get(1);
        Assert.assertEquals(property2.baseName, "name");
        Assert.assertEquals(property2.dataType, "String");
        Assert.assertEquals(property2.name, "name");
        Assert.assertNull(property2.defaultValue);
        Assert.assertEquals(property2.baseType, "String");
        Assert.assertTrue(property2.required);
        Assert.assertTrue(property2.isPrimitiveType);
        Assert.assertFalse(property2.isContainer);

        final CodegenProperty property3 = cm.vars.get(2);
        Assert.assertEquals(property3.baseName, "createdAt");
        Assert.assertEquals(property3.complexType, "DateTime");
        Assert.assertEquals(property3.dataType, "DateTime");
        Assert.assertEquals(property3.name, "createdAt");
        Assert.assertNull(property3.defaultValue);
        Assert.assertEquals(property3.baseType, "DateTime");
        Assert.assertFalse(property3.required);
        Assert.assertFalse(property3.isContainer);
    }

    @Test(description = "convert a simple dart-dit model with datelibrary")
    public void simpleModelWithTimeMachineTest() {
        final Schema model = new Schema()
                .description("a sample model")
                .addProperties("id", new IntegerSchema())
                .addProperties("name", new StringSchema())
                .addProperties("createdAt", new DateTimeSchema())
                .addProperties("birthDate", new DateSchema())
                .addRequiredItem("id")
                .addRequiredItem("name");

        final DartDioClientCodegen codegen = new DartDioClientCodegen();
        codegen.additionalProperties().put(DartDioClientCodegen.DATE_LIBRARY, DartDioClientCodegen.DATE_LIBRARY_TIME_MACHINE);
        codegen.processOpts();

        codegen.setOpenAPI(TestUtils.createOpenAPIWithOneSchema("sample", model));

        final CodegenModel cm = codegen.fromModel("sample", model);

        Assert.assertEquals(cm.name, "sample");
        Assert.assertEquals(cm.classname, "Sample");
        Assert.assertEquals(cm.description, "a sample model");
        Assert.assertEquals(cm.vars.size(), 4);
        // {{imports}} is not used in template
        //Assert.assertEquals(cm.imports.size(), 1);

        final CodegenProperty property1 = cm.vars.get(0);
        Assert.assertEquals(property1.baseName, "id");
        Assert.assertEquals(property1.dataType, "int");
        Assert.assertEquals(property1.name, "id");
        Assert.assertNull(property1.defaultValue);
        Assert.assertEquals(property1.baseType, "int");
        Assert.assertTrue(property1.required);
        Assert.assertTrue(property1.isPrimitiveType);
        Assert.assertFalse(property1.isContainer);

        final CodegenProperty property2 = cm.vars.get(1);
        Assert.assertEquals(property2.baseName, "name");
        Assert.assertEquals(property2.dataType, "String");
        Assert.assertEquals(property2.name, "name");
        Assert.assertNull(property2.defaultValue);
        Assert.assertEquals(property2.baseType, "String");
        Assert.assertTrue(property2.required);
        Assert.assertTrue(property2.isPrimitiveType);
        Assert.assertFalse(property2.isContainer);

        final CodegenProperty property3 = cm.vars.get(2);
        Assert.assertEquals(property3.baseName, "createdAt");
        Assert.assertEquals(property3.complexType, "OffsetDateTime");
        Assert.assertEquals(property3.dataType, "OffsetDateTime");
        Assert.assertEquals(property3.name, "createdAt");
        Assert.assertNull(property3.defaultValue);
        Assert.assertEquals(property3.baseType, "OffsetDateTime");
        Assert.assertFalse(property3.required);
        Assert.assertFalse(property3.isContainer);

        final CodegenProperty property4 = cm.vars.get(3);
        Assert.assertEquals(property4.baseName, "birthDate");
        Assert.assertEquals(property4.complexType, "OffsetDate");
        Assert.assertEquals(property4.dataType, "OffsetDate");
        Assert.assertEquals(property4.name, "birthDate");
        Assert.assertNull(property4.defaultValue);
        Assert.assertEquals(property4.baseType, "OffsetDate");
        Assert.assertFalse(property4.required);
        Assert.assertFalse(property4.isContainer);
    }

    @Test(description = "convert a model with list property")
    public void listPropertyTest() {
        final Schema model = new Schema()
                .description("a sample model")
                .addProperties("id", new IntegerSchema())
                .addProperties("urls", new ArraySchema()
                        .items(new StringSchema()))
                .addRequiredItem("id");

        final DefaultCodegen codegen = new DartDioClientCodegen();
        codegen.additionalProperties().put(CodegenConstants.SERIALIZATION_LIBRARY, DartDioClientCodegen.SERIALIZATION_LIBRARY_BUILT_VALUE);
        codegen.processOpts();

        codegen.setOpenAPI(TestUtils.createOpenAPIWithOneSchema("sample", model));

        final CodegenModel cm = codegen.fromModel("sample", model);

        Assert.assertEquals(cm.name, "sample");
        Assert.assertEquals(cm.classname, "Sample");
        Assert.assertEquals(cm.description, "a sample model");
        Assert.assertEquals(cm.vars.size(), 2);

        final CodegenProperty property1 = cm.vars.get(0);
        Assert.assertEquals(property1.baseName, "id");
        Assert.assertEquals(property1.dataType, "int");
        Assert.assertEquals(property1.name, "id");
        Assert.assertNull(property1.defaultValue);
        Assert.assertEquals(property1.baseType, "int");
        Assert.assertTrue(property1.required);
        Assert.assertTrue(property1.isPrimitiveType);
        Assert.assertFalse(property1.isContainer);

        final CodegenProperty property2 = cm.vars.get(1);
        Assert.assertEquals(property2.baseName, "urls");
        Assert.assertEquals(property2.dataType, "BuiltList<String>");
        Assert.assertEquals(property2.name, "urls");
        Assert.assertEquals(property2.baseType, "BuiltList");
        Assert.assertEquals(property2.containerType, "array");
        Assert.assertFalse(property2.required);
        Assert.assertTrue(property2.isPrimitiveType);
        Assert.assertTrue(property2.isContainer);
    }

    @Test(description = "convert a model with set property")
    public void setPropertyTest() {
        final Schema model = new Schema()
                .description("a sample model")
                .addProperties("id", new IntegerSchema())
                .addProperties("urls", new ArraySchema().items(new StringSchema()).uniqueItems(true))
                .addRequiredItem("id");

        final DefaultCodegen codegen = new DartDioClientCodegen();
        codegen.additionalProperties().put(CodegenConstants.SERIALIZATION_LIBRARY, DartDioClientCodegen.SERIALIZATION_LIBRARY_BUILT_VALUE);
        codegen.processOpts();

        codegen.setOpenAPI(TestUtils.createOpenAPIWithOneSchema("sample", model));

        final CodegenModel cm = codegen.fromModel("sample", model);

        Assert.assertEquals(cm.name, "sample");
        Assert.assertEquals(cm.classname, "Sample");
        Assert.assertEquals(cm.description, "a sample model");
        Assert.assertEquals(cm.vars.size(), 2);

        final CodegenProperty property1 = cm.vars.get(0);
        Assert.assertEquals(property1.baseName, "id");
        Assert.assertEquals(property1.dataType, "int");
        Assert.assertEquals(property1.name, "id");
        Assert.assertNull(property1.defaultValue);
        Assert.assertEquals(property1.baseType, "int");
        Assert.assertTrue(property1.required);
        Assert.assertTrue(property1.isPrimitiveType);
        Assert.assertFalse(property1.isContainer);

        final CodegenProperty property2 = cm.vars.get(1);
        Assert.assertEquals(property2.baseName, "urls");
        Assert.assertEquals(property2.dataType, "BuiltSet<String>");
        Assert.assertEquals(property2.name, "urls");
        Assert.assertEquals(property2.baseType, "BuiltSet");
        Assert.assertEquals(property2.containerType, "set");
        Assert.assertFalse(property2.required);
        Assert.assertTrue(property2.isPrimitiveType);
        Assert.assertTrue(property2.isContainer);
    }

    @Test(description = "convert a model with a map property")
    public void mapPropertyTest() {
        final Schema model = new Schema()
                .description("a sample model")
                .addProperties("translations", new MapSchema()
                        .additionalProperties(new StringSchema()))
                .addRequiredItem("id");

        final DefaultCodegen codegen = new DartDioClientCodegen();
        codegen.additionalProperties().put(CodegenConstants.SERIALIZATION_LIBRARY, DartDioClientCodegen.SERIALIZATION_LIBRARY_BUILT_VALUE);
        codegen.processOpts();

        codegen.setOpenAPI(TestUtils.createOpenAPIWithOneSchema("sample", model));

        final CodegenModel cm = codegen.fromModel("sample", model);

        Assert.assertEquals(cm.name, "sample");
        Assert.assertEquals(cm.classname, "Sample");
        Assert.assertEquals(cm.description, "a sample model");
        Assert.assertEquals(cm.vars.size(), 1);

        final CodegenProperty property1 = cm.vars.get(0);
        Assert.assertEquals(property1.baseName, "translations");
        Assert.assertEquals(property1.dataType, "BuiltMap<String, String>");
        Assert.assertEquals(property1.name, "translations");
        Assert.assertEquals(property1.baseType, "BuiltMap");
        Assert.assertEquals(property1.containerType, "map");
        Assert.assertFalse(property1.required);
        Assert.assertTrue(property1.isContainer);
        Assert.assertTrue(property1.isPrimitiveType);
    }

    @Test(description = "convert a model with complex property")
    public void complexPropertyTest() {
        final Schema model = new Schema()
                .description("a sample model")
                .addProperties("children", new Schema().$ref("#/definitions/Children"));

        final DefaultCodegen codegen = new DartDioClientCodegen();
        codegen.additionalProperties().put(CodegenConstants.SERIALIZATION_LIBRARY, DartDioClientCodegen.SERIALIZATION_LIBRARY_BUILT_VALUE);
        codegen.processOpts();

        codegen.setOpenAPI(TestUtils.createOpenAPIWithOneSchema("sample", model));

        final CodegenModel cm = codegen.fromModel("sample", model);

        Assert.assertEquals(cm.name, "sample");
        Assert.assertEquals(cm.classname, "Sample");
        Assert.assertEquals(cm.description, "a sample model");
        Assert.assertEquals(cm.vars.size(), 1);

        final CodegenProperty property1 = cm.vars.get(0);
        Assert.assertEquals(property1.baseName, "children");
        Assert.assertEquals(property1.dataType, "Children");
        Assert.assertEquals(property1.name, "children");
        Assert.assertEquals(property1.baseType, "Children");
        Assert.assertFalse(property1.required);
        Assert.assertFalse(property1.isContainer);
    }

    @Test(description = "convert a model with complex list property")
    public void complexListProperty() {
        final Schema model = new Schema()
                .description("a sample model")
                .addProperties("children", new ArraySchema()
                        .items(new Schema().$ref("#/definitions/Children")));

        final DefaultCodegen codegen = new DartDioClientCodegen();
        codegen.additionalProperties().put(CodegenConstants.SERIALIZATION_LIBRARY, DartDioClientCodegen.SERIALIZATION_LIBRARY_BUILT_VALUE);
        codegen.processOpts();

        codegen.setOpenAPI(TestUtils.createOpenAPIWithOneSchema("sample", model));

        final CodegenModel cm = codegen.fromModel("sample", model);

        Assert.assertEquals(cm.name, "sample");
        Assert.assertEquals(cm.classname, "Sample");
        Assert.assertEquals(cm.description, "a sample model");
        Assert.assertEquals(cm.vars.size(), 1);

        final CodegenProperty property1 = cm.vars.get(0);
        Assert.assertEquals(property1.baseName, "children");
        Assert.assertEquals(property1.dataType, "BuiltList<Children>");
        Assert.assertEquals(property1.name, "children");
        Assert.assertEquals(property1.baseType, "BuiltList");
        Assert.assertEquals(property1.containerType, "array");
        Assert.assertFalse(property1.required);
        Assert.assertTrue(property1.isContainer);
    }

    @Test(description = "convert a model with complex map property")
    public void complexMapSchema() {
        final Schema model = new Schema()
                .description("a sample model")
                .addProperties("children", new MapSchema()
                        .additionalProperties(new Schema().$ref("#/definitions/Children")));

        final DefaultCodegen codegen = new DartDioClientCodegen();
        codegen.additionalProperties().put(CodegenConstants.SERIALIZATION_LIBRARY, DartDioClientCodegen.SERIALIZATION_LIBRARY_BUILT_VALUE);
        codegen.processOpts();

        codegen.setOpenAPI(TestUtils.createOpenAPIWithOneSchema("sample", model));

        final CodegenModel cm = codegen.fromModel("sample", model);

        Assert.assertEquals(cm.name, "sample");
        Assert.assertEquals(cm.classname, "Sample");
        Assert.assertEquals(cm.description, "a sample model");
        Assert.assertEquals(cm.vars.size(), 1);

        final CodegenProperty property1 = cm.vars.get(0);
        Assert.assertEquals(property1.baseName, "children");
        Assert.assertEquals(property1.complexType, "Children");
        Assert.assertEquals(property1.dataType, "BuiltMap<String, Children>");
        Assert.assertEquals(property1.name, "children");
        Assert.assertEquals(property1.baseType, "BuiltMap");
        Assert.assertEquals(property1.containerType, "map");
        Assert.assertFalse(property1.required);
        Assert.assertTrue(property1.isContainer);
    }

    @Test(description = "convert an array model")
    public void arrayModelTest() {
        final Schema model = new ArraySchema()
                .items(new Schema().$ref("#/definitions/Children"))
                .description("an array model");
        final DefaultCodegen codegen = new DartDioClientCodegen();
        OpenAPI openAPI = TestUtils.createOpenAPIWithOneSchema("sample", model);
        codegen.setOpenAPI(openAPI);
        final CodegenModel cm = codegen.fromModel("sample", model);

        Assert.assertEquals(model.getDescription(), "an array model");

        Assert.assertEquals(cm.name, "sample");
        Assert.assertEquals(cm.classname, "Sample");
        Assert.assertTrue(cm.isArray);
        Assert.assertEquals(cm.description, "an array model");
        Assert.assertEquals(cm.vars.size(), 0);
    }

    @Test(description = "convert a map model")
    public void mapModelTest() {
        final Schema model = new Schema()
                .description("a map model")
                .additionalProperties(new Schema().$ref("#/definitions/Children"));
        final DefaultCodegen codegen = new DartDioClientCodegen();
        OpenAPI openAPI = TestUtils.createOpenAPIWithOneSchema("sample", model);
        codegen.setOpenAPI(openAPI);
        final CodegenModel cm = codegen.fromModel("sample", model);

        Assert.assertEquals(cm.name, "sample");
        Assert.assertEquals(cm.classname, "Sample");
        Assert.assertEquals(cm.description, "a map model");
        Assert.assertEquals(cm.vars.size(), 0);
    }

    @DataProvider(name = "modelNames")
    public static Object[][] modelNames() {
        return new Object[][]{
                {"EnumClass", "TestModelEnumClass"},
                {"JsonObject", "TestModelJsonObject"},
        };
    }

    @Test(dataProvider = "modelNames", description = "correctly prefix reserved model names")
    public void modelNameTest(String name, String expectedName) {
        OpenAPI openAPI = TestUtils.createOpenAPI();
        final Schema model = new Schema();

        final DefaultCodegen codegen = new DartDioClientCodegen();
        codegen.additionalProperties().put(CodegenConstants.SERIALIZATION_LIBRARY, DartDioClientCodegen.SERIALIZATION_LIBRARY_BUILT_VALUE);
        codegen.processOpts();
        codegen.typeMapping().put("EnumClass", "TestModelEnumClass");
        codegen.typeMapping().put("JsonObject", "TestModelJsonObject");
        codegen.setOpenAPI(openAPI);

        final CodegenModel cm = codegen.fromModel(name, model);

        Assert.assertEquals(cm.name, name);
        Assert.assertEquals(cm.classname, expectedName);
    }

    @DataProvider(name = "modelNamesTimemachine")
    public static Object[][] modelNamesTimemachine() {
        return new Object[][]{
                {"EnumClass", "TestModelEnumClass"},
                {"JsonObject", "TestModelJsonObject"},
                {"OffsetDate", "TestModelOffsetDate"},
        };
    }

    @Test(dataProvider = "modelNamesTimemachine", description = "correctly prefix reserved model names")
    public void modelNameTestTimemachine(String name, String expectedName) {
        OpenAPI openAPI = TestUtils.createOpenAPI();
        final Schema model = new Schema();
        final DartDioClientCodegen codegen = new DartDioClientCodegen();
        codegen.additionalProperties().put(DartDioClientCodegen.DATE_LIBRARY, DartDioClientCodegen.DATE_LIBRARY_TIME_MACHINE);
        codegen.processOpts();
        codegen.typeMapping().put("EnumClass", "TestModelEnumClass");
        codegen.typeMapping().put("JsonObject", "TestModelJsonObject");
        codegen.typeMapping().put("OffsetDate", "TestModelOffsetDate");
        codegen.setOpenAPI(openAPI);

        final CodegenModel cm = codegen.fromModel(name, model);

        Assert.assertEquals(cm.name, name);
        Assert.assertEquals(cm.classname, expectedName);
    }

    @Test(description = "correctly generate collection default values")
    public void collectionDefaultValues() {
        final ArraySchema array = new ArraySchema();
        array.setDefault("[]");
        final Schema model = new Schema()
                .description("a sample model")
                .addProperties("arrayNoDefault", new ArraySchema())
                .addProperties("arrayEmptyDefault", array)
                .addProperties("mapNoDefault", new MapSchema());
        final DefaultCodegen codegen = new DartDioClientCodegen();
        codegen.additionalProperties().put(CodegenConstants.SERIALIZATION_LIBRARY, DartDioClientCodegen.SERIALIZATION_LIBRARY_BUILT_VALUE);
        codegen.setOpenAPI(TestUtils.createOpenAPIWithOneSchema("sample", model));
        codegen.processOpts();
        final CodegenModel cm = codegen.fromModel("sample", model);

        final CodegenProperty arrayNoDefault = cm.vars.get(0);
        Assert.assertEquals(arrayNoDefault.name, "arrayNoDefault");
        Assert.assertNull(arrayNoDefault.defaultValue);

        final CodegenProperty arrayEmptyDefault = cm.vars.get(1);
        Assert.assertEquals(arrayEmptyDefault.name, "arrayEmptyDefault");
        Assert.assertEquals(arrayEmptyDefault.defaultValue, "ListBuilder()");

        final CodegenProperty mapNoDefault = cm.vars.get(2);
        Assert.assertEquals(mapNoDefault.name, "mapNoDefault");
        Assert.assertNull(mapNoDefault.defaultValue);
    }

    @Test(description = "correctly generate date/datetime default values, currently null")
    public void dateDefaultValues() {
        final DateSchema date = new DateSchema();
        date.setDefault("2021-01-01");
        final DateTimeSchema dateTime = new DateTimeSchema();
        dateTime.setDefault("2021-01-01T14:00:00Z");
        final Schema model = new Schema()
                .description("a sample model")
                .addProperties("date", date)
                .addProperties("dateTime", dateTime)
                .addProperties("mapNoDefault", new MapSchema());
        final DefaultCodegen codegen = new DartDioClientCodegen();
        OpenAPI openAPI = TestUtils.createOpenAPIWithOneSchema("sample", model);
        codegen.setOpenAPI(openAPI);
        final CodegenModel cm = codegen.fromModel("sample", model);

        final CodegenProperty dateDefault = cm.vars.get(0);
        Assert.assertEquals(dateDefault.name, "date");
        Assert.assertNull(dateDefault.defaultValue);

        final CodegenProperty dateTimeDefault = cm.vars.get(1);
        Assert.assertEquals(dateTimeDefault.name, "dateTime");
        Assert.assertNull(dateTimeDefault.defaultValue);
    }
}
