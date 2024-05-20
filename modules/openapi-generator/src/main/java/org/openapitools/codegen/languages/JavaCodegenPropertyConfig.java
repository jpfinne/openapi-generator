package org.openapitools.codegen.languages;

import java.util.List;
import java.util.function.BiConsumer;

class JavaCodegenPropertyConfig {
    boolean useBeanValidation;
    boolean useOpenapiNullable;
    boolean useOptionalForField;
    boolean useOptionalForGetter;

    boolean useOptionalNullable = false;
    BiConsumer<JavaCodegenProperty, List<String>> extraAnnotations;

    protected void defaultExtraAnnotation(JavaCodegenProperty property, List<String> list) {
        if (property.vendorExtensions.containsKey("x-field-extra-annotation")) {
            list.add(property.vendorExtensions.get("x-field-extra-annotation").toString());
        }
        if (property.isContainer && useBeanValidation) {
            list.add("@Valid");
        }
        if (!property.isContainer) {
            if (property.isDateTime) {
                list.add("@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)");
            } else if (property.isDate) {
                list.add("@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)");
            }
        }
    }

    public String getOptionalMethod(String name) {
        return (useOptionalNullable ? "Optional.ofNullable(" : "Optional.of(") + name + ")";
    }
}
