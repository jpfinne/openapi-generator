package org.openapitools.codegen.languages;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import org.apache.commons.lang3.StringUtils;
import org.openapitools.codegen.CodegenProperty;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

class JavaCodegenProperty extends CodegenProperty {
    private final JavaCodegenPropertyConfig config;
    private String beanValidation;

    JavaCodegenProperty(JavaCodegenPropertyConfig config) {
        this.config = config;
    }

    public String getFieldDeclaration() {
        return getFieldDeclaration(config.useOptionalForField);
    }

    public String getFieldDeclaration(boolean useOptional) {
        if (config.useOpenapiNullable && isNullable) {
            if (isArray) {
                return "JsonNullable<" + datatypeWithEnum + ">";
            }
            return "JsonNullable<" + savedBeanValidation() + datatypeWithEnum + ">";
        }
        if (useOptional && !required && !isContainer) {
            return "Optional<" + savedBeanValidation() + datatypeWithEnum + ">";
        }

        return datatypeWithEnum;
    }


    public String getGetterReturnDataType() {
        return getFieldDeclaration(config.useOptionalForGetter);
    }

    public String getGetterReturnValue() {
        if (config.useOpenapiNullable && isNullable) {
            return name;
        }
        if (config.useOptionalForGetter && !required && !isContainer && !config.useOptionalForField) {
            return config.getOptionalMethod(name);
        }
        return name;
    }

    public String getFieldDefaultValue() {
        if (config.useOpenapiNullable && isNullable) {
            return "JsonNullable.<" + datatypeWithEnum + ">undefined()";
        }
        if (isContainer) {
            if (StringUtils.isNotEmpty(defaultValue)) {
                return defaultValue;
            }
            if (!isNullable) {
                if (!config.useOpenapiNullable) {
                    if (isArray) {
                        return "new ArrayList<>()";
                    }
                    if (isMap) {
                        return "new HashMap<>()";
                    }
                }
            }
        } else {

            if (!config.useOpenapiNullable && isNullable) {
                return "null";
            }
            if (config.useOptionalForField && !required) {
                if (StringUtils.isNotEmpty(defaultValue)) {
                    return "Optional.of(" + defaultValue + ")";
                } else {
                    return "Optional.empty()";
                }
            }
            return defaultValue;
        }
        return null;
    }

    private String savedBeanValidation() {
        return StringUtils.isNotEmpty(beanValidation) ? beanValidation + " " : "";
    }

    public List<String> getFieldAnnotations() {
        List<String> annotations = new ArrayList<>();
        config.extraAnnotations.accept(this, annotations);
        if (deprecated) {
            annotations.add("@Deprecated");
        }
//            if (StringUtils.isNotEmpty(beanValidation)) {
//                annotations.add(beanValidation);
//            }
        return annotations;
    }

    public String getRightExpression() {
        if (config.useOpenapiNullable && isNullable) {
            return "JsonNullable.of(" + name + ")";
        }
        if (!required && !isContainer && config.useOptionalForField) {
            return config.getOptionalMethod(name);
        }
        return name;
    }

    public String getConstructorAssignment() {
        if (config.useOpenapiNullable && isNullable) {
            return "JsonNullable.of(" + name + ")";
        }
        if (!required && !isContainer && config.useOptionalForField) {
            return "Optional.ofNullable(" + name + ")";
        }
        return name;
    }

    public String getSetterArgument() {

        if (config.useOpenapiNullable && isNullable) {

            return "JsonNullable<" + datatypeWithEnum + ">";
        }
        if (config.useOptionalForField && !required && !isContainer) {
            return "Optional<" + datatypeWithEnum + ">";
        }

        return datatypeWithEnum;
    }

    public Mustache.Lambda getSaveBeanValidation() {
        return new Mustache.Lambda() {

            @Override
            public void execute(Template.Fragment fragment, Writer writer) throws IOException {
                if (config.useBeanValidation) {
                    String annotations = fragment.execute();
                    beanValidation = annotations.trim();
                } else {
                    beanValidation = null;
                }
            }
        };
    }
}
