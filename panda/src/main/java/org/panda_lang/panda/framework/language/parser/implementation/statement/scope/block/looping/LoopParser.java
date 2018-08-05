/*
 * Copyright (c) 2015-2018 Dzikoysk
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

package org.panda_lang.panda.framework.language.parser.implementation.statement.scope.block.looping;

import org.panda_lang.panda.framework.design.interpreter.parser.PandaPipelines;
import org.panda_lang.panda.framework.design.interpreter.parser.pipeline.ParserRegistration;
import org.panda_lang.panda.framework.language.interpreter.pattern.abyss.utils.AbyssPatternAssistant;
import org.panda_lang.panda.framework.language.interpreter.pattern.abyss.utils.AbyssPatternBuilder;
import org.panda_lang.panda.framework.design.interpreter.parser.ParserData;
import org.panda_lang.panda.framework.design.interpreter.parser.UnifiedParser;
import org.panda_lang.panda.framework.design.interpreter.token.TokenizedSource;
import org.panda_lang.panda.framework.language.interpreter.parser.PandaParserException;
import org.panda_lang.panda.framework.language.interpreter.pattern.abyss.AbyssPattern;
import org.panda_lang.panda.framework.language.interpreter.pattern.abyss.redactor.AbyssRedactor;
import org.panda_lang.panda.language.runtime.expression.Expression;
import org.panda_lang.panda.framework.language.parser.implementation.general.expression.ExpressionParser;
import org.panda_lang.panda.framework.design.architecture.dynamic.block.looping.LoopBlock;
import org.panda_lang.panda.framework.language.interpreter.token.PandaSyntax;
import org.panda_lang.panda.framework.language.parser.implementation.statement.scope.block.*;

@ParserRegistration(target = PandaPipelines.BLOCK, parserClass = LoopParser.class, handlerClass = LoopParserHandler.class)
public class LoopParser implements UnifiedParser {

    protected static final AbyssPattern PATTERN = new AbyssPatternBuilder()
            .compile(PandaSyntax.getInstance(), "loop ( +* )")
            .build();

    @Override
    public void parse(ParserData data) {
        AbyssRedactor redactor = AbyssPatternAssistant.traditionalMapping(PATTERN, data, "loop-expression");
        TokenizedSource expressionSource = redactor.get("loop-expression");

        ExpressionParser expressionParser = new ExpressionParser();
        Expression expression = expressionParser.parse(data, expressionSource);

        if (!expression.getReturnType().isClassOf("Int")) {
            throw new PandaParserException("Loop requires number as an argument");
        }

        data.setComponent(BlockComponents.BLOCK, new LoopBlock(expression));
    }

}