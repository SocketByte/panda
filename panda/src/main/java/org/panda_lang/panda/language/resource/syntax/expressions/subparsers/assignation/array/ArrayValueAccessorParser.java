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

package org.panda_lang.panda.language.resource.syntax.expressions.subparsers.assignation.array;

import org.jetbrains.annotations.Nullable;
import org.panda_lang.framework.design.architecture.expression.Expression;
import org.panda_lang.framework.design.interpreter.parser.Components;
import org.panda_lang.framework.design.interpreter.parser.Context;
import org.panda_lang.framework.design.interpreter.parser.Parser;
import org.panda_lang.framework.design.interpreter.parser.expression.ExpressionParser;
import org.panda_lang.framework.design.interpreter.token.Snippet;
import org.panda_lang.framework.design.interpreter.token.Snippetable;
import org.panda_lang.framework.design.interpreter.token.TokenInfo;
import org.panda_lang.framework.language.architecture.type.array.ArrayType;
import org.panda_lang.framework.language.interpreter.parser.PandaParserFailure;
import org.panda_lang.framework.language.resource.syntax.TokenTypes;
import org.panda_lang.framework.language.resource.syntax.auxiliary.Section;
import org.panda_lang.framework.language.resource.syntax.separator.Separators;

public final class ArrayValueAccessorParser implements Parser {

    public @Nullable ArrayAccessor parse(Context context, Snippet source) {
        TokenInfo sectionRepresentation = source.getLast();

        if (sectionRepresentation == null || sectionRepresentation.getType() != TokenTypes.SECTION) {
            return null;
        }

        Section section = sectionRepresentation.toToken();

        if (!section.getSeparator().equals(Separators.SQUARE_BRACKET_LEFT)) {
            return null;
        }

        Snippet instanceSource = source.subSource(0, source.size() - 1);
        ExpressionParser parser = context.getComponent(Components.EXPRESSION);
        Expression instance = parser.parse(context, instanceSource).getExpression();

        return parse(context, source, instance, section);
    }

    public ArrayAccessor parse(Context context, Snippetable source, Expression instance, Section indexSource) {
        ExpressionParser parser = context.getComponent(Components.EXPRESSION);
        Expression index = parser.parse(context, indexSource.getContent()).getExpression();

        if (!index.getType().getAssociatedClass().isAssignableTo(Integer.class)) {
            throw new PandaParserFailure(context, source, "The specified index is not an integer", "Change array index to expression that returns int");
        }

        return of(context, source, instance, index);
    }

    public ArrayAccessor of(Context context, Snippetable source, Expression instance, Expression index) {
        if (!instance.getType().isArray()) {
            throw new PandaParserFailure(context, source, "Cannot use index on non-array type (" + instance.getType() + ")");
        }

        ArrayType arrayType = (ArrayType) instance.getType();

        if (arrayType == null) {
            throw new PandaParserFailure(context, source, "Cannot locate array class");
        }

        return new ArrayAccessor(instance, index);
    }

}
