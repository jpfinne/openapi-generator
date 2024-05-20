package org.openapitools.codegen.templating.mustache;

import org.testng.annotations.Test;

import java.util.Map;

public class UnindentTest extends LambdaTest {
    @Test
    void unindent() {
        // Given
        Map<String, Object> ctx = context("unindent", new Unindent(), "useOptional", true);

        // When & Then
        test("var test = Optional.ofNullable()", "\n{{#unindent}}\nvar test = \n  Optional.ofNullable()\n{{/unindent}}\n", ctx);
        test("var test = Optional.ofNullable()", "{{#unindent}}\nvar test = \n  {{#useOptional}}Optional.ofNullable()\n  {{/useOptional}}\n{{/unindent}}", ctx);
        test("var test = Optional.ofNullable()", "{{#unindent}}\nvar test =\n   {{#useOptional}}Optional.ofNullable()\n  {{/useOptional}}\n{{/unindent}}", ctx);
        test("var test = Optional.ofNullable()", "\n{{#unindent}}\nvar test = \n  Optional.ofNullable()\n{{/unindent}}\n", ctx);
    }
}
