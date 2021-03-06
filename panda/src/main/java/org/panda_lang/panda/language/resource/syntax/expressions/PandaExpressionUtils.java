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

package org.panda_lang.panda.language.resource.syntax.expressions;

import org.panda_lang.framework.PandaFrameworkException;
import org.panda_lang.framework.design.architecture.module.ModuleLoaderUtils;
import org.panda_lang.framework.design.architecture.type.Type;
import org.panda_lang.framework.design.interpreter.parser.expression.ExpressionContext;
import org.panda_lang.framework.design.interpreter.parser.expression.ExpressionSubparser;
import org.panda_lang.framework.design.interpreter.parser.expression.ExpressionSubparsers;
import org.panda_lang.framework.language.interpreter.parser.expression.PandaExpressionSubparsers;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class PandaExpressionUtils {

    private PandaExpressionUtils() { }

    public static ExpressionSubparsers collectSubparsers() {
        return collectSubparsers(PandaExpressions.SUBPARSERS);
    }

    @SafeVarargs
    public static ExpressionSubparsers collectSubparsers(Class<? extends ExpressionSubparser>... classes) {
        return new PandaExpressionSubparsers(Arrays.stream(classes)
                .map(clazz -> {
                    try {
                        return (ExpressionSubparser) clazz.newInstance();
                    } catch (InstantiationException | IllegalAccessException e) {
                        throw new PandaFrameworkException(e);
                    }
                })
                .collect(Collectors.toList()));
    }

    public static Type forClass(ExpressionContext expressionContext, Class<?> associatedClass) {
        return ModuleLoaderUtils.requireType(expressionContext.getContext(), associatedClass);
    }

}
