/*
 * Copyright 2018 OpenAPI-Generator Contributors (https://openapi-generator.tech)
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

package org.openapitools.codegen.templating.mustache;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template.Fragment;

import java.io.IOException;
import java.io.Writer;

/**
 * merge several lines
 *
 * Register:
 * <pre>
 * additionalProperties.put("unindent", new Unindent());
 * </pre>
 *
 * Use:
 * <pre>
 * {{#unindent}}
 *   var myVar =
 *      {{#isList}}List{{/isList}}
 *      {{#isMap}}List{{/isMap}}
 *      ;
 * {{/unindent}}
 * </pre>
 */
public class Unindent implements Mustache.Lambda {


    @Override
    public void execute(Fragment fragment, Writer writer) throws IOException {
        String str = fragment.execute();
        merge(str, writer);
    }

    static void merge(String str, Writer writer) throws IOException {
        String[] lines = str.split("[\r\n]+");
        if (lines.length == 1) {
            writer.write(str);
            return;
        }
//        int initialIndentation = getIndentation(lines[0]);
//        writer.write(lines[0].substring(0, initialIndentation));
        int indentation = 0;
        int lastIdx = lines.length - 1;
        for (int idx = 0; idx <=lastIdx; idx++) {
            String line = lines[idx];
            if (line.isEmpty()) {
                continue;
            }
            int newIndentation = getIndentation(line);
            if (newIndentation >= 0) {
//                if (newIndentation > indentation) {
//                    if (newIndentation > indentation && newIndentation %2 == 1) {
//                        // add a space if the new indentation is odd
//                        writer.write(" ");
//                    }
//                }
                indentation = newIndentation;

                String value = line.substring(indentation).replace("¶", "\n").replace(" ", " ");
                writer.write(value);
            } else {
                if (idx > 0 || idx < lastIdx) {
                    //writer.write("\n");
                }
            }
        }
    }

    static int getIndentation(String str) {
        if (str.startsWith(" ")) {
            int len = str.length();
            for (int i=1; i < len; i++) {
                if (str.charAt(i) != ' ') {
                    return i;
                }
            }
            return -1;
        }
        return 0;
    }
}
