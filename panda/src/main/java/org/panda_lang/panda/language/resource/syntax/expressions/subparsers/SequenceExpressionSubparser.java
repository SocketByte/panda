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

package org.panda_lang.panda.language.resource.syntax.expressions.subparsers;

import org.jetbrains.annotations.Nullable;
import org.panda_lang.framework.design.architecture.module.ModuleLoaderUtils;
import org.panda_lang.framework.design.architecture.type.Type;
import org.panda_lang.framework.design.interpreter.parser.Context;
import org.panda_lang.framework.design.interpreter.parser.expression.ExpressionContext;
import org.panda_lang.framework.design.interpreter.parser.expression.ExpressionResult;
import org.panda_lang.framework.design.interpreter.parser.expression.ExpressionSubparser;
import org.panda_lang.framework.design.interpreter.parser.expression.ExpressionSubparserWorker;
import org.panda_lang.framework.design.interpreter.token.TokenInfo;
import org.panda_lang.framework.language.interpreter.parser.expression.ExpressionParserUtils;
import org.panda_lang.framework.language.interpreter.token.TokenUtils;

public final class SequenceExpressionSubparser implements ExpressionSubparser {

    @Override
    public ExpressionSubparserWorker createWorker(Context context) {
        return new SequenceWorker(context).withSubparser(this);
    }

    @Override
    public String getSubparserName() {
        return "sequence";
    }

    private static final class SequenceWorker extends AbstractExpressionSubparserWorker implements ExpressionSubparserWorker {

        private final Type stringType;

        private SequenceWorker(Context context) {
            this.stringType = ModuleLoaderUtils.requireType(context, String.class);
        }

        @Override
        public @Nullable ExpressionResult next(ExpressionContext context, TokenInfo token) {
            if (TokenUtils.hasName(token, "String")) {
                return ExpressionParserUtils.toExpressionResult(stringType, token.getValue());
            }

            return null;
        }

    }

}
