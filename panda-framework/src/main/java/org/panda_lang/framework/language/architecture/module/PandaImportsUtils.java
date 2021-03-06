/*
 * Copyright (c) 2015-2020 Dzikoysk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.panda_lang.framework.language.architecture.module;

import io.vavr.control.Option;
import org.jetbrains.annotations.Nullable;
import org.panda_lang.framework.design.architecture.module.Imports;
import org.panda_lang.framework.design.architecture.type.Type;
import org.panda_lang.framework.design.interpreter.parser.Components;
import org.panda_lang.framework.design.interpreter.parser.Context;
import org.panda_lang.framework.design.interpreter.token.Snippet;
import org.panda_lang.framework.design.interpreter.token.Snippetable;
import org.panda_lang.framework.language.interpreter.parser.PandaParserFailure;
import org.panda_lang.utilities.commons.text.MessageFormatter;

import java.util.function.Function;
import java.util.function.Supplier;

public final class PandaImportsUtils {

    private PandaImportsUtils() { }

    public static Type getTypeOrThrow(Context context, Snippetable nameSource, String message, String note) {
        String name = nameSource.toSnippet().asSource();

        return context.getComponent(Components.IMPORTS)
                .forName(name)
                .getOrElseThrow((Supplier<? extends PandaParserFailure>) () -> {
                    MessageFormatter formatter = new MessageFormatter();
                    formatter.register("{name}", name);

                    throw new PandaParserFailure(context, nameSource, formatter.format(message), formatter.format(note));
                });
    }

    public static Type getTypeOrThrow(Context context, String className, @Nullable Snippet source) {
        return getTypeOrThrow(context, imports -> imports.forName(className), "Unknown type " + className, source);
    }

    private static Type getTypeOrThrow(Context context, Function<Imports, Option<Type>> mapper, String message, Snippet source) {
        return mapper.apply(context.getComponent(Components.IMPORTS)).getOrElse(() -> {
            throw new PandaParserFailure(context, source, message);
        });
    }

}
